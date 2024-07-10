package com.example.cargame.managers;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.cargame.models.Character;
import com.example.cargame.models.Player;
import com.example.cargame.R;
import com.example.cargame.interfaces.PlayerNameCallback;

public class GameManager implements PlayerNameCallback {

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
    private final MusicManager music, collisionSound;
    private final Vibrator vibrator;
    private HighScoreManager scoreManager;
    private Location currentLocation;
    private final Handler difficultyHandler = new Handler();
    private LocationManager locationManager;

    public GameManager(Context context, UIManager uiManager, Character character, ObstacleManager obstacleManager) {
        this.context = context;
        this.uiManager = uiManager;
        this.character = character;
        this.obstacleManager = obstacleManager;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.scoreManager = new HighScoreManager(context);

        music = new MusicManager(context, R.raw.background_music);
        collisionSound= new MusicManager(context, R.raw.ouchsound);
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public void startGame() {
        if (locationManager != null) {
            locationManager.getLastLocation(location -> {
                if (location != null) {
                    currentLocation = location;
                    startGameInternal();
                }
            });
        } else {
            startGameInternal();
        }
    }

    private void startGameInternal() {
        gameOver = false;
        music.startMusic(true);
        resetGame();
        handler.postDelayed(gameRunnable, delayTimer);
        difficultyHandler.postDelayed(difficultyRunnable, 10000);
    }

    private final Runnable gameRunnable= new Runnable() {
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
            handler.postDelayed(this, delayTimer);
        }
    };

    private final Runnable difficultyRunnable = new Runnable() {
        @Override
        public void run() {
            if (gameOver) return;
            dummyTimer = dummyTimer * 0.9;
            delayTimer = (long) dummyTimer;
            difficultyHandler.postDelayed(this, 10000); // קביעת תזמון חדש לעוד 10 שניות
        }
    };

    public void onCollision(int obstacleImage) {
        if (obstacleImage == R.drawable.bonus) {
            character.upgrade();
        } else if (character.getCurrentImageResource() == R.drawable.gokuss3) {
            character.downgrade();
            music.momentMusic(music, collisionSound);
        } else {
            music.momentMusic(music, collisionSound);
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
        music.releaseExecutor();
    }

    private void vibratePhone() {
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void playerLost() {
        handler.removeCallbacksAndMessages(null);
        difficultyHandler.removeCallbacksAndMessages(null);
        music.stopMusic();
        uiManager.showPlayerNameDialog(this);
    }

    @Override
    public void onPlayerNameEntered(String playerName, boolean save) {
        if (save) {
            if (currentLocation != null) {
                Player player = new Player(playerName, ticks, currentLocation.getLatitude(), currentLocation.getLongitude());
                scoreManager.addPlayer(player);
            } else {
                Player player = new Player(playerName, ticks, 0, 0); // ברירת מחדל אם לא נמצא מיקום
                scoreManager.addPlayer(player);
            }
        }
        uiManager.showStartGameDialog(this);
    }

}
