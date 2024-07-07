package com.example.cargame.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.cargame.managers.GameManager;
import com.example.cargame.models.Character;

public class MoveDetector {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private final Context context;
    private final Character character;
    private final GameManager gameManager;
    private long timestamp = 0L;
    private float startPosition[] = {0, 0, 0};
    private boolean gameStarted = false;

    private static final float MOVE_THRESHOLD = 2.0f;
    private static final float SPEED_THRESHOLD = 1.0f;

    public MoveDetector(Context context, Character character, GameManager gameManager) {
        this.context = context;
        this.character = character;
        this.gameManager = gameManager;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Log.d("MoveDetector", "Sensor started");
        }
        gameStarted = false;
        initEventListener();
    }

    private void initEventListener() {
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                if (!gameStarted) {
                    startPosition[0] = x;
                    startPosition[1] = y;
                    startPosition[2] = z;
                    gameStarted = true;
                }

                if (System.currentTimeMillis() - timestamp > 500) {
                    timestamp = System.currentTimeMillis();
                    if (x > startPosition[0] + MOVE_THRESHOLD) {
                        character.moveLeft();
                    } else if (x < startPosition[0] - MOVE_THRESHOLD) {
                        character.moveRight();
                    }

                    if (y > startPosition[1] + MOVE_THRESHOLD) {
                        character.moveUp();
                    } else if (y < startPosition[1] - MOVE_THRESHOLD) {
                        character.moveDown();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // לא נחוץ במשחק שלנו
            }
        };
    }

    public void start() {
        if (sensor != null) {
            sensorManager.registerListener(
                    sensorEventListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
            Log.d("MoveDetector", "Sensor started");
        } else {
            Log.e("MoveDetector", "Sensor not available");
        }
    }

    public void stop() {
        if (sensor != null) {
            sensorManager.unregisterListener(
                    sensorEventListener,
                    sensor
            );
            gameStarted = false;
            Log.d("MoveDetector", "Sensor stopped");
        }
    }
}
