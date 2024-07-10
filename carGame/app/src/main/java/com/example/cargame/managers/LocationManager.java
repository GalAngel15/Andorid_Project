package com.example.cargame.managers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationManager {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Activity activity;
    private static final int FINE_PERMISSION_CODE = 1;
    private final Location defaultLocation;

    public LocationManager(Activity activity, FusedLocationProviderClient fusedLocationProviderClient) {
        this.activity = activity;
        this.fusedLocationProviderClient = fusedLocationProviderClient;

        this.defaultLocation = new Location("");
        this.defaultLocation.setLatitude(32.0853);
        this.defaultLocation.setLongitude(34.7818);
    }

    public void checkLocationAccess() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    public void getLastLocation(OnSuccessListener<Location> onSuccessListener) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Location permissions not granted", Toast.LENGTH_SHORT).show();
            onSuccessListener.onSuccess(defaultLocation);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(onSuccessListener).addOnFailureListener(e -> {
            onSuccessListener.onSuccess(defaultLocation);
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Location permission is required for this app to work.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
