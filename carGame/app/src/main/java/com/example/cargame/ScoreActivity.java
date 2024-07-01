package com.example.cargame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private HighScoreManager highScoreManager;
    private Button buttonBack;
    private LinearLayout[] rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        highScoreManager = new HighScoreManager(this);
        initBoard();
        displayHighScores();
        initBtnBack();
    }

    private void initBoard() {
        rows = new LinearLayout[]{
                findViewById(R.id.row1), findViewById(R.id.row2), findViewById(R.id.row3),
                findViewById(R.id.row4), findViewById(R.id.row5), findViewById(R.id.row6),
                findViewById(R.id.row7), findViewById(R.id.row8), findViewById(R.id.row9),
                findViewById(R.id.row10)};
    }

    private void displayHighScores() {
        LinearLayout scoresLayout = findViewById(R.id.scoresLayout);
        //scoresLayout.removeAllViews(); // ניקוי השיאים הישנים

        List<HighScoreManager.Score> highScores = highScoreManager.getHighScores();
        for (int i = 0; i < highScores.size(); i++) {
            HighScoreManager.Score score = highScores.get(i);
            int rankId = getResources().getIdentifier("rank" + (i + 1), "id", getPackageName());
            int playerNameId = getResources().getIdentifier("playerName" + (i + 1), "id", getPackageName());
            int scoreId = getResources().getIdentifier("score" + (i + 1), "id", getPackageName());

            TextView rankView = findViewById(rankId);
            TextView playerNameView = findViewById(playerNameId);
            TextView scoreView = findViewById(scoreId);

            rankView.setText(String.valueOf(i + 1)); // מגדיר את הדירוג
            playerNameView.setText("שם שחקן " + (i + 1)); // שם השחקן
            scoreView.setText("1000"); // ניקוד השחקן

            // הפיכה ל-visible
            LinearLayout scoreRow = findViewById("scoreRow" + (i + 1));
            scoreRow.setVisibility(View.VISIBLE);
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
