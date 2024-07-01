package com.example.cargame;

import android.content.Context;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class GameManager {

    private int lives = 3;
    private int ticks = 0;
    private double dummyTimer = 1000;
    private long delayTimer = 1000;
    private boolean gameOver = false;
    private final Handler handler = new Handler();
    private final Context context;
    private final UIManager uiManager;
    private final Character character;
    private final ObstacleManager obstacleManager;
    private final MusicManager music;
    private final Vibrator vibrator;
    private HighScoreManager scoreManager;

    public GameManager(Context context, UIManager uiManager, Character character, ObstacleManager obstacleManager, MusicManager music) {
        this.context = context;
        this.uiManager = uiManager;
        this.character = character;
        this.obstacleManager = obstacleManager;
        this.music = music;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        scoreManager=new HighScoreManager(context);
    }

    public void startGame() {
        gameOver = false;
        music.startMusic();
        resetGame();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameOver) return;
                uiManager.updateScore(ticks);
                obstacleManager.moveAllObstaclesDown(character, GameManager.this);
                ticks++;
                if (ticks % 2 == 0) {
                    obstacleManager.addObstacle();
                } else if (ticks % 7 == 0) {
                    obstacleManager.addBonus();
                }
                if (ticks % 10 == 0) {
                    dummyTimer = dummyTimer * 0.9;
                    delayTimer = (long) dummyTimer;
                }
                handler.postDelayed(this, delayTimer);
            }
        }, delayTimer);
    }

    public void onCollision(int obstacleImage) {
        if (obstacleImage == R.drawable.bonus) {
            character.upgrade();
        } else if (character.getCurrentImageResource() == R.drawable.gokuss3) {
            character.downgrade();
        } else {
            lives--;
            vibratePhone();
            uiManager.updateLives(lives);
            if (lives <= 0) {
                gameOver = true;
                Toast.makeText(context, "Game Over!", Toast.LENGTH_SHORT).show();
                playerLost();
            } else {
                Toast.makeText(context, "Crash!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetGame() {
        lives = 3;
        ticks = 0;
        dummyTimer = 1000;
        delayTimer = 1000;
        uiManager.updateLives(lives);
        uiManager.updateScore(ticks);
        obstacleManager.clearObstacles();
        character.resetPosition();

    }

    public void stopGame() {
        music.stopMusic();
    }

    private void vibratePhone() {
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
    private void playerLost(){
        handler.removeCallbacksAndMessages(null);
        music.stopMusic();
        scoreManager.addScore(ticks, "gal");
        uiManager.showStartGameDialog(this);
    }
}
