package com.example.cargame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] grid;
    private int boyPositionRow = 8;
    private int boyPositionCol = 1;
    private int lives = 3;
    private Handler handler = new Handler();
    private int currentImageResource = R.drawable.boy; // משתנה לעקוב אחרי התמונה המוצגת
    private int delayTimer = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGrid();
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
        grid=new ImageView[][]{
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

    private void updateCarPosition() {
        //clearGrid();
        grid[boyPositionRow][boyPositionCol].setImageResource(currentImageResource);
    }

    private void clearGrid() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setImageResource(0);
            }
        }
    }

    private void moveCarLeft() {
        if (boyPositionCol > 0) {
            grid[boyPositionRow][boyPositionCol].setImageResource(0);
            boyPositionCol--;
            updateCarPosition();
        }
    }

    private void moveCarRight() {
        if (boyPositionCol < 2) {
            grid[boyPositionRow][boyPositionCol].setImageResource(0);
            boyPositionCol++;
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
        grid[0][lane].setImageResource(R.drawable.fast);

        moveObstacleDown(0, lane);
    }

    private void moveObstacleDown(final int row, final int col) {
        if (row < 8) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    grid[row][col].setImageResource(0);
                    grid[row + 1][col].setImageResource(R.drawable.fast);
                    moveObstacleDown(row + 1, col);
                }
            }, delayTimer);
        } else {
            grid[row][col].setImageResource(0);
        }
    }

    private void checkCollision() {
        if (grid[boyPositionRow - 1][boyPositionCol].getDrawable() != null &&
                grid[boyPositionRow - 1][boyPositionCol].getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.fast).getConstantState()) {
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
