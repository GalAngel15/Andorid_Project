package com.example.cargame;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleManager {

    private final List<Obstacle> obstacles = new ArrayList<>();
    private final ImageView[][] grid;
    private final int[] enemies = {R.drawable.frieza, R.drawable.kidbuu, R.drawable.goldenfrieza};
    private final int bounusImg = R.drawable.bonus;

    public ObstacleManager(ImageView[][] grid) {
        this.grid = grid;
    }

    public void addObstacle() {
        Random rand = new Random();
        int lane = rand.nextInt(3);
        int obstacleImage = enemies[rand.nextInt(enemies.length)];
        grid[0][lane].setImageResource(obstacleImage);
        obstacles.add(new Obstacle(0, lane, obstacleImage));
    }

    public void addBonus() {
        Random rand = new Random();
        int lane = rand.nextInt(3);
        grid[0][lane].setImageResource(bounusImg);
        obstacles.add(new Obstacle(0, lane, bounusImg));
    }

    public void moveAllObstaclesDown(Character character, GameManager gameManager) {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            if (!character.checkCollision(obstacle.getRow(), obstacle.getCol())) {
                grid[obstacle.getRow()][obstacle.getCol()].setImageResource(0);
            }
            if (obstacle.getRow() < 8) {
                if (!character.checkCollision(obstacle.getRow() + 1, obstacle.getCol())) {
                    obstacle.moveDown();
                    grid[obstacle.getRow()][obstacle.getCol()].setImageResource(obstacle.getImageResource());
                } else {
                    obstacles.remove(i);
                    gameManager.onCollision(obstacle.getImageResource());
                    i--;
                }
            } else { // obstacles at the last row
                obstacles.remove(i);
                i--;
            }
        }
    }

    public void clearObstacles() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setImageResource(0);
            }
        }
        obstacles.clear();
    }
}
