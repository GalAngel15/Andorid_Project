package com.example.cargame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cargame.R;
import com.example.cargame.managers.LocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class ManuActivity extends BaseActivity {

    private Button buttonSensorsMode;
    private Button buttonButtonsMode;
    private Button buttonHighScores;
    private String keyMode;
    private String buttonsMode;
    private String sensorsMode;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);
        keyMode = getResources().getString(R.string.key_mode);
        buttonsMode = getResources().getString(R.string.buttons_mode);
        sensorsMode = getResources().getString(R.string.sensors_mode);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = new LocationManager(this, fusedLocationProviderClient);
        locationManager.checkLocationAccess();

        initializeButtons();
        setListeners();
    }

    private void initializeButtons() {
        buttonSensorsMode = findViewById(R.id.buttonSensorsMode);
        buttonButtonsMode = findViewById(R.id.buttonButtonsMode);
        buttonHighScores = findViewById(R.id.buttonHighScores);
    }

    private void setListeners() {
        buttonSensorsMode.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(keyMode, sensorsMode);
            i.putExtras(bundle);
            startActivity(i);
        });
        buttonButtonsMode.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(keyMode, buttonsMode);
            i.putExtras(bundle);
            startActivity(i);
        });
        buttonHighScores.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ScoreActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationManager.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
