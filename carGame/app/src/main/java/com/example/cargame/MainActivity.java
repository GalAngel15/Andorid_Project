package com.example.cargame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] grid = new ImageView[8][3];
    private int carPositionRow = 7;
    private int carPositionCol = 1;
    private int lives = 3;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGrid();
        updateCarPosition();

        Button btnLeft = findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarLeft();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarRight();
            }
        });

        startGame();
    }

    private void initializeGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                String imageViewId = "grid_item_" + (i + 1) + "_" + (j + 1);
                int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());
                grid[i][j] = findViewById(resId);
            }
        }
    }

    private void updateCarPosition() {
        clearGrid();
        grid[carPositionRow][carPositionCol].setImageResource(R.drawable.car);
    }

    private void clearGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setImageResource(0);
            }
        }
    }

    private void moveCarLeft() {
        if (carPositionCol > 0) {
            carPositionCol--;
            updateCarPosition();
        }
    }

    private void moveCarRight() {
        if (carPositionCol < 2) {
            carPositionCol++;
            updateCarPosition();
        }
    }

    private void startGame() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addObstacle();
                checkCollision();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void addObstacle() {
        Random rand = new Random();
        int lane = rand.nextInt(3);
        grid[0][lane].setImageResource(R.drawable.obstacle);

        moveObstacleDown(0, lane);
    }

    private void moveObstacleDown(final int row, final int col) {
        if (row < 7) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    grid[row][col].setImageResource(0);
                    grid[row + 1][col].setImageResource(R.drawable.obstacle);
                    moveObstacleDown(row + 1, col);
                }
            }, 500);
        } else {
            grid[row][col].setImageResource(0);
        }
    }

    private void checkCollision() {
        if (grid[carPositionRow - 1][carPositionCol].getDrawable() != null &&
                grid[carPositionRow - 1][carPositionCol].getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.obstacle).getConstantState()) {
            lives--;
            updateLives();
            if (lives <= 0) {
                Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
                handler.removeCallbacksAndMessages(null);
            } else {
                Toast.makeText(this, "Crash!", Toast.LENGTH_SHORT).show();
            }
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
}
