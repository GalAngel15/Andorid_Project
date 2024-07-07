package com.example.cargame.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cargame.R;

public class ManuActivity extends AppCompatActivity {

    private Button buttonSensorsMode;
    private Button buttonButtonsMode;
    private Button buttonHighScores;
    private String keyMode;
    private String buttonsMode;
    private String sensorsMode;
    private static final int FINE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manu);
        keyMode = getResources().getString(R.string.key_mode);
        buttonsMode = getResources().getString(R.string.buttons_mode);
        sensorsMode = getResources().getString(R.string.sensors_mode);
        initializeButtons();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocationAccess();
    }

    private void checkLocationAccess() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
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
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission is required for this app to work.", Toast.LENGTH_SHORT).show();
                // ניתן להוסיף התנהגות נוספת אם ההרשאה נדחתה
            }
        }
    }
}
