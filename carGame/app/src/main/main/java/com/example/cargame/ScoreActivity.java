package com.example.cargame;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity implements OnMapReadyCallback {
    private HighScoreManager highScoreManager;
    private Button buttonBack;
    private LinearLayout[] rows;
    private TextView[] scores;
    private final int FINE_PREMISSION_CODE =1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        highScoreManager = new HighScoreManager(this);
        initBoard();
        displayHighScores();
        initBtnBack();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PREMISSION_CODE);
            return;

        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation=location;

                SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(ScoreActivity.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==FINE_PREMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap=googleMap;
        LatLng currentLocationLatLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("My location"));
        float zoomLevel = 15.0f; //1 smallest zoom level to 21 largest
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationLatLng,zoomLevel));

    }

    private void initBoard() {
        rows = new LinearLayout[]{
                findViewById(R.id.row1), findViewById(R.id.row2), findViewById(R.id.row3),
                findViewById(R.id.row4), findViewById(R.id.row5), findViewById(R.id.row6),
                findViewById(R.id.row7), findViewById(R.id.row8), findViewById(R.id.row9),
                findViewById(R.id.row10)};
        scores = new TextView[]{
                findViewById(R.id.score1), findViewById(R.id.score2), findViewById(R.id.score3),
                findViewById(R.id.score4), findViewById(R.id.score5), findViewById(R.id.score6),
                findViewById(R.id.score7), findViewById(R.id.score8), findViewById(R.id.score9),
                findViewById(R.id.score10)};
    }

    private void displayHighScores() {
        LinearLayout scoresLayout = findViewById(R.id.scoresLayout);
        //scoresLayout.removeAllViews(); // ניקוי השיאים הישנים

        ArrayList<Player> highScores = highScoreManager.getHighScores();
        for (int i = 0; i < highScores.size(); i++) {
            rows[i].setVisibility(View.VISIBLE);
            scores[i].setText(String.valueOf(highScores.get(i).getScore()));
        }
    }

    private void initBtnBack() {
        buttonBack = findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ManuActivity.class);
            startActivity(i);
        });
    }
}
