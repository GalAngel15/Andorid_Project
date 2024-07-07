package com.example.cargame;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static final String PREFS_NAME = "HighScores";
    private static final String SCORES_KEY = "Scores";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public HighScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }

    public void saveHighScore(String playerName, int score, double latitude, double longitude) {
        ArrayList<Player> players = getHighScores();
        players.add(new Player(playerName, score, latitude, longitude));

        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });

        // Keep only top 10 players
        if (players.size() > 10) {
            players.subList(10, players.size()).clear();
        }

        String json = gson.toJson(players);
        editor.putString(SCORES_KEY, json);
        editor.apply();
    }

    public ArrayList<Player> getHighScores() {
        String json = preferences.getString(SCORES_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type playerListType = new TypeToken<ArrayList<Player>>() {}.getType();
            return gson.fromJson(json, playerListType);
        }
    }
}
