package com.example.cargame.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.cargame.R;
import com.example.cargame.managers.HighScoreManager;
import com.example.cargame.managers.LocationManager;
import com.example.cargame.models.Player;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreActivity extends BaseActivity implements OnMapReadyCallback {
    private HighScoreManager highScoreManager;
    private Button buttonBack;
    private LinearLayout[] rows;
    private TextView[] scores;
    private TextView[] names;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private Map<String, LatLng> playerLocations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = new LocationManager(this, fusedLocationProviderClient);

        locationManager.checkLocationAccess();
        locationManager.getLastLocation(location -> {
            if (location != null) {
                currentLocation = location;

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(ScoreActivity.this);
            }
        });

        highScoreManager = new HighScoreManager(this);
        initBoard();
        displayHighScores();
        initBtnBack();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        if (currentLocation != null) {
            LatLng currentLocationLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            myMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("My location"));
            float zoomLevel = 15.0f; // 1 smallest zoom level to 21 largest
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationLatLng, zoomLevel));
        }
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
        names = new TextView[]{
                findViewById(R.id.playerName1), findViewById(R.id.playerName2), findViewById(R.id.playerName3),
                findViewById(R.id.playerName4), findViewById(R.id.playerName5), findViewById(R.id.playerName6),
                findViewById(R.id.playerName7), findViewById(R.id.playerName8), findViewById(R.id.playerName9),
                findViewById(R.id.playerName10)};
    }

    private void displayHighScores() {
        List<Player> highScores = highScoreManager.getHighScores();
        for (int i = 0; i < highScores.size(); i++) {
            rows[i].setVisibility(View.VISIBLE);
            Player player = highScores.get(i);
            names[i].setText(player.getName());
            scores[i].setText(String.valueOf(player.getScore()));

            LatLng playerLatLng = new LatLng(player.getLatitude(), player.getLongitude());
            playerLocations.put(player.getName(), playerLatLng);

            rows[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPlayerLocationOnMap(player.getName());
                }
            });
        }
        for (int i = highScores.size(); i < rows.length; i++) {
            rows[i].setVisibility(View.GONE);
        }
    }

    private void showPlayerLocationOnMap(String playerName) {
        LatLng playerLatLng = playerLocations.get(playerName);
        if (playerLatLng != null) {
            myMap.clear();
            myMap.addMarker(new MarkerOptions().position(playerLatLng).title(playerName + "'s location"));
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(playerLatLng, 15.0f));
        }
    }

    private void initBtnBack() {
        buttonBack = findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ManuActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationManager.onRequestPermissionsResult(requestCode, grantResults);
    }
}
