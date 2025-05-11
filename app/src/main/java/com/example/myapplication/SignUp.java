package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText mNameEditText, mEmailEditText, mPasswordEditText, mConfirmPasswordEditText, mPhoneEditText;

    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private ImageView mProfileImageView;
    private String uploadedImageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mProfileImageView = findViewById(R.id.profile_pic);
        mProfileImageView.setOnClickListener(v -> {
            requestStoragePermission();
            selectImage();
        });


        mNameEditText = findViewById(R.id.editText_name);
        mEmailEditText = findViewById(R.id.text_email);
        mPasswordEditText = findViewById(R.id.text_password);
        mConfirmPasswordEditText = findViewById(R.id.text_password_confirm);
        mPhoneEditText = findViewById(R.id.text_contact);

        findViewById(R.id.btn_sign_up).setOnClickListener(v -> signUpUser());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            mProfileImageView.setImageURI(imageUri); // Display the selected image in the ImageView
        }
    }


    private void signUpUser() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        String name = mNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || phone.isEmpty() || imageUri == null) {
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload image to ImageBB
        uploadImageToImageBB(imageUri, url -> {
            uploadedImageUrl = url;
            saveUserToFirebase(email, password, name, phone, url);
        });
    }

    private void uploadImageToImageBB(Uri imageUri, OnImageUploadListener listener) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();

        String uploadUrl = "https://api.imgbb.com/1/upload?key=a9b91ce901cc620e57c3192bc5562b1e";

        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                    response -> {
                        progressDialog.dismiss();
                        Log.d("ImageUpload", "Response: " + response);
                        // Parse response to extract the URL
                        String uploadedUrl = parseImageUrlFromResponse(response);
                        if (uploadedUrl != null) {
                            listener.onUploadSuccess(uploadedUrl);
                        } else {
                            Toast.makeText(SignUp.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "Image upload failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("image", encodedImage);
                    return params;
                }
            };

            queue.add(stringRequest);
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Error reading image file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserToFirebase(String email, String password, String name, String phone, String imageUrl) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            DatabaseReference userRef = mDatabase.child("users").child(user.getUid());
                            User userData = new User(name, phone, email, imageUrl);

                            userRef.setValue(userData).addOnCompleteListener(databaseTask -> {
                                if (databaseTask.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(SignUp.this, HomeActivity.class);
                                    startActivity(homeIntent);
                                    finish(); // Finish SignUp activity so the user can't go back to it
                                } else {
                                    Toast.makeText(SignUp.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String parseImageUrlFromResponse(String response) {
        // Parse the JSON response and extract the image URL
        // For example, use JSONObject to parse the response
        return "parsed_image_url"; // Replace with actual parsing logic
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }



    interface OnImageUploadListener {
        void onUploadSuccess(String url);
    }
}
