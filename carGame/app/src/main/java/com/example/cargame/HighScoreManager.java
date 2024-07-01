package com.example.cargame;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {
    private static final String PREFS_NAME = "HighScores";
    private static final String SCORES_KEY = "Scores";
    private SharedPreferences preferences;

    public HighScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addScore(int score, String playerName) {
        List<Score> scores = getHighScores();
        scores.add(new Score(score, playerName));
        Collections.sort(scores);
        if (scores.size() > 10) {
            scores.remove(scores.size() - 1);
        }
        saveScores(scores);
    }

    public List<Score> getHighScores() { //read it from shared Pref
        List<Score> scores = new ArrayList<>();
        String savedScores = preferences.getString(SCORES_KEY, "");//saved as "200,gal;300,tal;"
        if (!savedScores.isEmpty()) {
            String[] scoreEntries = savedScores.split(";");
            for (String entry : scoreEntries) {
                String[] parts = entry.split(",");
                if (parts.length == 2) {
                    try {
                        int score = Integer.parseInt(parts[0]);
                        String playerName = parts[1];
                        scores.add(new Score(score, playerName));
                    } catch (NumberFormatException e) {
                        // התעלם מערכים שלא ניתן להמיר ל-Integer
                    }
                }
            }
        }
        Collections.sort(scores);
        return scores;
    }

    private void saveScores(List<Score> scores) {
        StringBuilder savedScores = new StringBuilder();
        for (Score score : scores) {
            savedScores.append(score.getScore()).append(",").append(score.getPlayerName()).append(";"); //save as "200,gal;300,tal;"
        }
        preferences.edit().putString(SCORES_KEY, savedScores.toString()).apply();
    }

    public static class Score implements Comparable<Score> {
        private final int score;
        private final String playerName;

        public Score(int score, String playerName) {
            this.score = score;
            this.playerName = playerName;
        }

        public int getScore() {
            return score;
        }

        public String getPlayerName() {
            return playerName;
        }

        @Override
        public int compareTo(Score other) {
            return Integer.compare(other.score, this.score);
        }
    }
}
