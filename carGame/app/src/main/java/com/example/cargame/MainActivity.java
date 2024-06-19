package com.example.cargame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] grid;
    private int characterPositionRow = 8;
    private int characterPositionCol = 1;
    private int lives = 3;
    private final Handler handler = new Handler();
    private int currentImageResource = R.drawable.goku; // משתנה לעקוב אחרי התמונה המוצגת
    private double dummyTimer = 1000;
    private long delayTimer = 1000;
    private int ticks = 0;
    private final int[] enemies = {R.drawable.frieza, R.drawable.kidbuu, R.drawable.goldenfrieza};
    private final List<Obstacle> obstacles = new ArrayList<>();
    private boolean gameOver = false;
    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGrid();
        updateScore();
        MaterialButton btnLeft = findViewById(R.id.btn_left);
        MaterialButton btnRight = findViewById(R.id.btn_right);
        MaterialButton btnUp = findViewById(R.id.btn_up);
        MaterialButton btnDown = findViewById(R.id.btn_down);

        btnLeft.setOnClickListener(v->moveCharacterLeft());

        btnRight.setOnClickListener(v->moveCharacterRight());

        btnUp.setOnClickListener(v->moveCharacterUp());

        btnDown.setOnClickListener(v->moveCharacterDown());
        playSound();
        startGame();
    }

    private void initializeGrid() {
        grid = new ImageView[][]{
                {findViewById(R.id.grid_item_0_0), findViewById(R.id.grid_item_0_1), findViewById(R.id.grid_item_0_2)},
                {findViewById(R.id.grid_item_1_0), findViewById(R.id.grid_item_1_1), findViewById(R.id.grid_item_1_2)},
                {findViewById(R.id.grid_item_2_0), findViewById(R.id.grid_item_2_1), findViewById(R.id.grid_item_2_2)},
                {findViewById(R.id.grid_item_3_0), findViewById(R.id.grid_item_3_1), findViewById(R.id.grid_item_3_2)},
                {findViewById(R.id.grid_item_4_0), findViewById(R.id.grid_item_4_1), findViewById(R.id.grid_item_4_2)},
                {findViewById(R.id.grid_item_5_0), findViewById(R.id.grid_item_5_1), findViewById(R.id.grid_item_5_2)},
                {findViewById(R.id.grid_item_6_0), findViewById(R.id.grid_item_6_1), findViewById(R.id.grid_item_6_2)},
                {findViewById(R.id.grid_item_7_0), findViewById(R.id.grid_item_7_1), findViewById(R.id.grid_item_7_2)},
                {findViewById(R.id.grid_item_8_0), findViewById(R.id.grid_item_8_1), findViewById(R.id.grid_item_8_2)}
        };
    }

    private void updateCurrentImage() {
        grid[characterPositionRow][characterPositionCol].setImageResource(currentImageResource);
    }

    private void moveCharacterLeft() {
        if (characterPositionCol > 0) {
            grid[characterPositionRow][characterPositionCol].setImageResource(0);
            characterPositionCol--;
            updateCurrentImage();
        }
    }

    private void moveCharacterRight() {
        if (characterPositionCol < 2) {
            grid[characterPositionRow][characterPositionCol].setImageResource(0);
            characterPositionCol++;
            updateCurrentImage();
        }
    }

    private void moveCharacterUp() {
        if (characterPositionRow > 0) {
            grid[characterPositionRow][characterPositionCol].setImageResource(0);
            characterPositionRow--;
            updateCurrentImage();
        }
    }

    private void moveCharacterDown() {
        if (characterPositionRow < 8) {
            grid[characterPositionRow][characterPositionCol].setImageResource(0);
            characterPositionRow++;
            updateCurrentImage();
        }
    }

    private void startGame() {
        gameOver = false;
        playSound();
        initialState();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(gameOver)
                    return;
                updateScore();
                moveAllObstaclesDown();
                ticks++;
                if (ticks % 2 == 0) {
                    addObstacle(enemies[new Random().nextInt(enemies.length)]);
                } else if (ticks % 5 == 0) {
                    addObstacle(R.drawable.boxing_gloves); //give shield
                }
                if (ticks % 10 == 0) {
                    dummyTimer = dummyTimer * 0.9; //make the game faster
                    delayTimer = (long) dummyTimer;
                }
                handler.postDelayed(this, delayTimer);
            }
        }, delayTimer);
    }

    private void addObstacle(final int obstacleImage) {
        Random rand = new Random();
        int lane = rand.nextInt(3);
        grid[0][lane].setImageResource(obstacleImage);
        obstacles.add(new Obstacle(0, lane, obstacleImage));
    }

    private void moveAllObstaclesDown() {
        for (int i=0; i<obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            if(!(characterPositionRow == obstacle.getRow() && characterPositionCol == obstacle.getCol())) {
                grid[obstacle.getRow()][obstacle.getCol()].setImageResource(0);
            }
            if (obstacle.getRow() < 8) {
                if (!checkCollision(obstacle.getRow() + 1, obstacle.getCol(), obstacle.getImageResource())) {
                    obstacle.moveDown();
                    grid[obstacle.getRow()][obstacle.getCol()].setImageResource(obstacle.getImageResource());
                }else {
                    obstacles.remove(i);
                    i--;
                }
            } else {
                obstacles.remove(i);
                i--;
                checkCollision(obstacle.getRow(), obstacle.getCol(), obstacle.getImageResource());
            }
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setImageResource(0);
                obstacles.clear();
            }
        }
    }

    private boolean checkCollision(final int row, final int col, final int obstacleImage) {
        if (row == characterPositionRow && col == characterPositionCol && obstacleImage == R.drawable.boxing_gloves) {
            currentImageResource = R.drawable.gokuss1;
            updateCurrentImage();
            return true;
        } else if (row == characterPositionRow && col == characterPositionCol) {
            collision();
            return true;
        }
        return false;
    }

    private void collision() {
        if (currentImageResource == R.drawable.gokuss1) {
            currentImageResource = R.drawable.goku;
        } else {
            lives--;
            updateLives();
        }
        updateCurrentImage();
        vibratePhone(); //need to check this
        if (lives <= 0) {
            gameOver = true;
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacksAndMessages(null);
            onDestroy();
            showStartGameDialog();
        } else {
            Toast.makeText(this, "Crash!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLives() {
        ImageView heart1 = findViewById(R.id.heart1);
        ImageView heart2 = findViewById(R.id.heart2);
        ImageView heart3 = findViewById(R.id.heart3);
        heart1.setVisibility(lives >= 1 ? View.VISIBLE : View.INVISIBLE);
        heart2.setVisibility(lives >= 2 ? View.VISIBLE : View.INVISIBLE);
        heart3.setVisibility(lives >= 3 ? View.VISIBLE : View.INVISIBLE);
    }

    // Function to create vibration
    private void vibratePhone() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        }
    }

    private void updateScore() {
        TextView scoreTextView = findViewById(R.id.score);
        String scoreText = getString(R.string.score_text, ticks);
        scoreTextView.setText(scoreText);
    }

    private void showStartGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("האם אתה רוצה להתחיל משחק חדש?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "המשחק מתחיל!", Toast.LENGTH_SHORT).show();
                        startGame();
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void initialState() {
        clearGrid();
        lives = 3;
        updateLives();
        ticks = 0;
        dummyTimer = 1000;
        delayTimer = 1000;
        characterPositionRow = 8;
        characterPositionCol = 1;
        updateCurrentImage();
        updateScore();
    }
    private void playSound() {
        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);

        // Start playing background music
        mediaPlayer.start();
    }
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
