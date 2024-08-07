package com.example.cargame.models;

public class Obstacle {
    private int row;
    private int col;
    private int imageResource;

    public Obstacle(int row, int col, int imageResource) {
        this.row = row;
        this.col = col;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void moveDown() {
        row++;
    }
}

