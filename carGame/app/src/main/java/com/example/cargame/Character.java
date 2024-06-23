package com.example.cargame;

import android.widget.ImageView;

public class Character {

    private int positionRow = 8;
    private int positionCol = 1;
    private int currentImageResource = R.drawable.goku;
    private final ImageView[][] grid;

    public Character(ImageView[][] grid) {
        this.grid = grid;
        updateCurrentImage();
    }

    public void moveLeft() {
        if (positionCol > 0) {
            grid[positionRow][positionCol].setImageResource(0);
            positionCol--;
            updateCurrentImage();
        }
    }

    public void moveRight() {
        if (positionCol < 2) {
            grid[positionRow][positionCol].setImageResource(0);
            positionCol++;
            updateCurrentImage();
        }
    }

    public void moveUp() {
        if (positionRow > 1) {
            grid[positionRow][positionCol].setImageResource(0);
            positionRow--;
            updateCurrentImage();
        }
    }

    public void moveDown() {
        if (positionRow < 8) {
            grid[positionRow][positionCol].setImageResource(0);
            positionRow++;
            updateCurrentImage();
        }
    }

    public void upgrade() {
        currentImageResource = R.drawable.gokuss3;
        updateCurrentImage();
    }
    public void downgrade() {
        currentImageResource = R.drawable.goku;
        updateCurrentImage();
    }

    public void resetPosition() {
        positionRow = 8;
        positionCol = 1;
        currentImageResource = R.drawable.goku;
        updateCurrentImage();
    }

    public boolean checkCollision(int row, int col) {
        return row == positionRow && col == positionCol;
    }

    private void updateCurrentImage() {
        grid[positionRow][positionCol].setImageResource(currentImageResource);
    }

    public int getCurrentImageResource() {
        return currentImageResource;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }
}
