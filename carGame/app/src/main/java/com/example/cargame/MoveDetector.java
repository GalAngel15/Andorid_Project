package com.example.cargame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MoveDetector {

    private SensorManager sensorManager; //מדבר עם כל הסנסורים
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private Context context;
    private final Character character;
    private GameManager gameManager;
    private long timestamp = 0l;
    private float startPosition[] = {0,0};
    private boolean gameStarted =false;


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
            Log.d("MoveDetector1", "Sensor started1");
        }
        initEventListener();
    }

    private void initEventListener() {
        Log.d("MoveDetector1", "Sensor started2");
        this.sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) { //initEventListener in Tom's example
                float x = event.values[0];
                float y = event.values[1];
                if(!gameStarted) {
                    startPosition[0]=x;
                    startPosition[1]=y;
                    gameStarted =true;
                }
                Log.d("MoveDetector1", "Sensor started4");
//                if (System.currentTimeMillis() - timestamp > 500){
//                    timestamp = System.currentTimeMillis();
//                    if (x > 6.0 || x < -6.0){
//                        character.moveRight();
//                    }
//                    if (y > 6.0 || y < -6.0){
//                        character.moveUp();
//                    }
//                }
                if (x > startPosition[0]+6.0) {
                    character.moveLeft();
                } else if (x < -(startPosition[0]+6.0)) {
                    character.moveRight();
                }

                if (y > startPosition[1]+6.0) {
                    character.moveUp();
                } else if (y < -(startPosition[1]+6.0)) {
                    character.moveDown();
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
            Log.d("MoveDetector0", "Sensor started");
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
            gameStarted =false;
            Log.d("MoveDetector", "Sensor stopped");
        }
    }
}
