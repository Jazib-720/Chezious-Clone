package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ImageView btnCancel;
    private SmartMaterialSpinner<String> spinnerCity, spinnerArea;
    private Button btnSelect;
    private LinearLayout btnMap;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        // Initialize Views
        btnCancel = findViewById(R.id.btn_cancel);
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerArea = findViewById(R.id.spinner_area);
        btnSelect = findViewById(R.id.btn_select);
        btnMap = findViewById(R.id.btn_map);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Handle cancel button click
        btnCancel.setOnClickListener(v -> {
            Toast.makeText(LocationActivity.this, "Cancel button clicked", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        });

        // Handle select button click
        btnSelect.setOnClickListener(v -> {
            String selectedCity = spinnerCity.getSelectedItem();
            String selectedArea = spinnerArea.getSelectedItem();

            if (selectedCity == null || selectedArea == null) {
                Toast.makeText(this, "Please select both city and area", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selected: " + selectedCity + ", " + selectedArea, Toast.LENGTH_SHORT).show();
            }
        });

        // Handle map button click
        btnMap.setOnClickListener(v -> {
            Toast.makeText(this, "Locate on map clicked", Toast.LENGTH_SHORT).show();
            // Request location permission if not granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getLocationAndShowOnMap();
            }
        });

        // Set up the map fragment using locfrag
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void getLocationAndShowOnMap() {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Get the last known location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Create a LatLng object for the current location
                            LatLng currentLocation = new LatLng(latitude, longitude);

                            // Move the camera to the current location
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                            // Add a marker for the current location
                            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));

                            Toast.makeText(LocationActivity.this, "Current Location: " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LocationActivity.this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(this, e -> {
                        Toast.makeText(LocationActivity.this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndShowOnMap(); // Permission granted, fetch location
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
