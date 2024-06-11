package com.example.cargame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] grid;
    private int boyPositionRow = 8;
    private int boyPositionCol = 1;
    private int lives = 3;
    private Handler handler = new Handler();
    private int currentImageResource = R.drawable.boy; // משתנה לעקוב אחרי התמונה המוצגת
    private double dummyTimer = 1000;
    private long delayTimer = 1000;
    private int ticks = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGrid();
        updateScore();
        Button btnLeft = findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterLeft();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCharacterRight();
            }
        });
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
        grid[boyPositionRow][boyPositionCol].setImageResource(currentImageResource);
    }

    private void moveCharacterLeft() {
        if (boyPositionCol > 0) {
            grid[boyPositionRow][boyPositionCol].setImageResource(0);
            boyPositionCol--;
            updateCurrentImage();
        }
    }

    private void moveCharacterRight() {
        if (boyPositionCol < 2) {
            grid[boyPositionRow][boyPositionCol].setImageResource(0);
            boyPositionCol++;
            updateCurrentImage();
        }
    }

    private void startGame() {
        initialState();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateScore();
                ticks++;
                if (ticks % 2 == 0) {
                    addObstacle(R.drawable.fast);
                } else if (ticks % 5 == 0) {
                    addObstacle(R.drawable.boxing_gloves); //give shield
                }
                if (ticks % 10 == 0) {
                    dummyTimer = dummyTimer * 0.9; //make the game faster
                    delayTimer = (long) dummyTimer;
                }
                //checkCollision();
                handler.postDelayed(this, delayTimer);
            }
        }, delayTimer);
    }

    private void addObstacle(final int obstacleImage) {
        Random rand = new Random();
        int lane = rand.nextInt(3);
        grid[0][lane].setImageResource(obstacleImage);
        moveObstacleDown(0, lane, obstacleImage);
    }

    private void moveObstacleDown(final int row, final int col, final int obstacleImage) {
        if (row < 8) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    grid[row][col].setImageResource(0);
                    grid[row + 1][col].setImageResource(obstacleImage);
                    moveObstacleDown(row + 1, col, obstacleImage);
                }
            }, delayTimer);
        } else {
            grid[row][col].setImageResource(0);
            if (col == boyPositionCol && obstacleImage == R.drawable.fast) {
                collision();
                updateCurrentImage();
            } else if (col == boyPositionCol && obstacleImage == R.drawable.boxing_gloves) {
                currentImageResource = R.drawable.viking;
                updateCurrentImage();
            }
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setImageResource(0);
            }
        }
    }
    private void collision() {
        if(currentImageResource == R.drawable.viking){
            currentImageResource=R.drawable.boy;
            updateCurrentImage();
        }else {
            lives--;
            updateLives();
        }
        vibratePhone(); //need to check this
        if (lives <= 0) {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacksAndMessages(null);
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
        boyPositionRow = 8;
        boyPositionCol = 1;
        updateCurrentImage();
        updateScore();
    }
}
