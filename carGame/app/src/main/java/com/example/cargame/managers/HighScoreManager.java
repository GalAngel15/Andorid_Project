package com.example.cargame.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cargame.models.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
    private static final String PREFS_NAME = "HighScores";
    private static final String SCORES_KEY = "Scores";
    private SharedPreferences preferences;
    private Gson gson;

    public HighScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void addPlayer(Player player) {
        List<Player> players = getHighScores();
        players.add(player);
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        if (players.size() > 10) {
            players.remove(players.size() - 1);
        }
        saveScores(players);
    }

    public List<Player> getHighScores() {
        String json = preferences.getString(SCORES_KEY, "");
        Type type = new TypeToken<List<Player>>() {}.getType();
        List<Player> players = gson.fromJson(json, type);
        return players == null ? new ArrayList<>() : players;
    }

    private void saveScores(List<Player> players) {
        String json = gson.toJson(players);
        preferences.edit().putString(SCORES_KEY, json).apply();
    }
}