package com.example.cargame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MoveDetector {

    private SensorManager sensorManager; //מדבר עם כל הסנסורים
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private Context context;
    private Character character;
    private GameManager gameManager;

    // סף תזוזה לתנועה
    private static final float MOVE_THRESHOLD = 2.0f;
    private static final float SPEED_THRESHOLD = 1.0f;

    public MoveDetector(Context context, Character character, GameManager gameManager) {
        this.context = context;
        this.character = character;
        this.gameManager = gameManager;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        initEventListener();
    }

    private void initEventListener() {
        this.sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) { //initEventListener in Tom's example
                float x = event.values[0];
                float y = event.values[1];

                if (x > MOVE_THRESHOLD) {
                    character.moveLeft();
                } else if (x < -MOVE_THRESHOLD) {
                    character.moveRight();
                }

                if (y > SPEED_THRESHOLD) {
                    character.moveUp();
                } else if (y < -SPEED_THRESHOLD) {
                    character.moveDown();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // לא נחוץ במשחק שלנו
            }
        };
    }

    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
