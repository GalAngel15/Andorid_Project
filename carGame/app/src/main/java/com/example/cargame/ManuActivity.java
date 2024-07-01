package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManuActivity extends AppCompatActivity {

    private Button buttonSensorsMode;
    private Button buttonButtonsMode;
    private Button buttonHighScores;
    private String keyMode;
    private String buttonsMode;
    private String sensorsMode;

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

}