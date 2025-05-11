package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class PhoneSignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId; // to store the verification ID sent by Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonesignin);

        mAuth = FirebaseAuth.getInstance();






        Button btn_login = findViewById(R.id.btn_login);
        TextView textContinueAsGuest = findViewById(R.id.text_skip);
        EditText textContact = findViewById(R.id.text_contact);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otp, null);

        builder.setView(dialogView).setCancelable(true);
        AlertDialog dialog = builder.create();

        // Find views inside the OTP dialog
        EditText otpInput = dialogView.findViewById(R.id.otp_input);
        Button submitButton = dialogView.findViewById(R.id.submit_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);

        btn_login.setOnClickListener(v -> {
            String phoneNumber = textContact.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            } else {
                sendVerificationCode(phoneNumber);
                dialog.show();
            }
        });

        submitButton.setOnClickListener(v -> {
            String code = otpInput.getText().toString().trim();
            if (code.isEmpty() || code.length() < 6) {
                otpInput.setError("Enter a valid OTP");
                otpInput.requestFocus();
                return;
            }

            verifyCode(code); // Verify the entered OTP
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        textContinueAsGuest.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to verify the OTP
    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        } catch (Exception e) {
            Log.e("PhoneSignInActivity", "Invalid code: " + e.getMessage());
            Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Successfully signed in
                        Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show();

                        // Store login status in SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isAuthenticated", true);  // Set authentication status to true
                        editor.apply();

                        // Log the stored value to verify it's set
                        boolean isAuthenticated = prefs.getBoolean("isAuthenticated", false);
                        Log.d("SharedPreferences", "isAuthenticated: " + isAuthenticated);  // Log the value

                        // Navigate to HomeActivity
                        Intent intent = new Intent(this, SignUp.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign-in failed
                        Toast.makeText(this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        Toast.makeText(PhoneSignInActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(PhoneSignInActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        PhoneSignInActivity.this.verificationId = verificationId;
                        Toast.makeText(PhoneSignInActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }}