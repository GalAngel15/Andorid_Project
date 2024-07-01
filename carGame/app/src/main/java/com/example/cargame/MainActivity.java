package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] grid;
    private Character character;
    private GameManager gameManager;
    private UIManager uiManager;
    private ObstacleManager obstacleManager;
    private MaterialButton leftButton, rightButton, upButton, downButton;
    private MusicManager music;
    private MoveDetector moveDetector;
    private String mode;
    private String sensorsMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGrid();
        uiManager = new UIManager(this);
        character = new Character(grid);
        obstacleManager = new ObstacleManager(grid);
        music = new MusicManager(this, R.raw.background_music);
        gameManager = new GameManager(this, uiManager, character, obstacleManager, music);
        String keyMode = getResources().getString(R.string.key_mode);
        String buttonsMode = getResources().getString(R.string.buttons_mode);
        sensorsMode=getResources().getString(R.string.sensors_mode);
        setMode();
        if (mode.equals(buttonsMode)) {
            initializeButtons();
        } else if (mode.equals(sensorsMode)) {
            initializeSensors();
        }
        uiManager.statGame(gameManager);
    }

    private void initializeSensors() {
        moveDetector = new MoveDetector(this, character, gameManager);
    }

    private void setMode() {
        Intent prev = getIntent();
        mode= Objects.requireNonNull(prev.getExtras()).getString("mode");
    }

    private void initializeButtons() {
        leftButton = findViewById(R.id.btn_left);
        rightButton = findViewById(R.id.btn_right);
        upButton = findViewById(R.id.btn_up);
        downButton = findViewById(R.id.btn_down);

        leftButton.setOnClickListener(v -> character.moveLeft());
        rightButton.setOnClickListener(v -> character.moveRight());
        upButton.setOnClickListener(v -> character.moveUp());
        downButton.setOnClickListener(v -> character.moveDown());
    }

    private void initializeGrid() {
        grid = new ImageView[][]{
                {findViewById(R.id.grid_item_0_0), findViewById(R.id.grid_item_0_1), findViewById(R.id.grid_item_0_2), findViewById(R.id.grid_item_0_3),findViewById(R.id.grid_item_0_4)},
                {findViewById(R.id.grid_item_1_0), findViewById(R.id.grid_item_1_1), findViewById(R.id.grid_item_1_2), findViewById(R.id.grid_item_1_3), findViewById(R.id.grid_item_1_4)},
                {findViewById(R.id.grid_item_2_0), findViewById(R.id.grid_item_2_1), findViewById(R.id.grid_item_2_2), findViewById(R.id.grid_item_2_3), findViewById(R.id.grid_item_2_4)},
                {findViewById(R.id.grid_item_3_0), findViewById(R.id.grid_item_3_1), findViewById(R.id.grid_item_3_2), findViewById(R.id.grid_item_3_3), findViewById(R.id.grid_item_3_4)},
                {findViewById(R.id.grid_item_4_0), findViewById(R.id.grid_item_4_1), findViewById(R.id.grid_item_4_2), findViewById(R.id.grid_item_4_3), findViewById(R.id.grid_item_4_4)},
                {findViewById(R.id.grid_item_5_0), findViewById(R.id.grid_item_5_1), findViewById(R.id.grid_item_5_2), findViewById(R.id.grid_item_5_3), findViewById(R.id.grid_item_5_4)},
                {findViewById(R.id.grid_item_6_0), findViewById(R.id.grid_item_6_1), findViewById(R.id.grid_item_6_2), findViewById(R.id.grid_item_6_3), findViewById(R.id.grid_item_6_4)},
                {findViewById(R.id.grid_item_7_0), findViewById(R.id.grid_item_7_1), findViewById(R.id.grid_item_7_2), findViewById(R.id.grid_item_7_3), findViewById(R.id.grid_item_7_4)},
                {findViewById(R.id.grid_item_8_0), findViewById(R.id.grid_item_8_1), findViewById(R.id.grid_item_8_2), findViewById(R.id.grid_item_8_3), findViewById(R.id.grid_item_8_4)}
        };
    }
    @Override
    protected void onStop() {
        super.onStop();
        gameManager.stopGame();
        if(moveDetector!=null)
            moveDetector.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mode.equals(sensorsMode))
            moveDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.stopGame();
        if(moveDetector!=null)
            moveDetector.stop();
    }

    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer when the activity is destroyed
        gameManager.stopGame();
        if(moveDetector!=null)
            moveDetector.stop();
    }
}
