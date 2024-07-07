package com.example.cargame.managers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.cargame.R;
import com.example.cargame.activities.MainActivity;
import com.example.cargame.activities.ManuActivity;
import com.example.cargame.interfaces.PlayerNameCallback;

public class UIManager {

    private final Context context;
    private final ImageView heart1;
    private final ImageView heart2;
    private final ImageView heart3;
    private final TextView scoreTextView;

    public UIManager(Context context) {
        this.context = context;
        heart1 = ((MainActivity) context).findViewById(R.id.heart1);
        heart2 = ((MainActivity) context).findViewById(R.id.heart2);
        heart3 = ((MainActivity) context).findViewById(R.id.heart3);
        scoreTextView = ((MainActivity) context).findViewById(R.id.score);
    }

    public void updateLives(int lives) {
        heart1.setVisibility(lives >= 1 ? View.VISIBLE : View.INVISIBLE);
        heart2.setVisibility(lives >= 2 ? View.VISIBLE : View.INVISIBLE);
        heart3.setVisibility(lives >= 3 ? View.VISIBLE : View.INVISIBLE);
    }

    public void updateScore(int score) {
        String scoreText = context.getString(R.string.score_text, score);
        scoreTextView.setText(scoreText);
    }

    public void statGame(GameManager gameManager){
        gameManager.startGame();
    }
    
    public void showPlayerNameDialog(PlayerNameCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Your Name");

        // יצירת פריסה מותאמת אישית
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText input = new EditText(context);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(input);

        final TextView errorText = new TextView(context);
        errorText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        errorText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        errorText.setVisibility(View.GONE);
        layout.addView(errorText);

        builder.setView(layout);

        builder.setPositiveButton("OK", null); // Will be created
        builder.setNegativeButton("Cancel", (dialog, which) ->{
                callback.onPlayerNameEntered("", false); //Player didn't want to save name
                dialog.dismiss();
                });

        AlertDialog dialog = builder.create();

        // Positive btn (OK)
        dialog.setOnShowListener(dlg -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String playerName = input.getText().toString();
                if (playerName.isEmpty()) {
                    errorText.setText("Must enter a name or cancel");
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    callback.onPlayerNameEntered(playerName, true);
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    public void showStartGameDialog(GameManager gameManager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Start a new game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "המשחק מתחיל!", Toast.LENGTH_SHORT).show();
                        gameManager.startGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(context.getApplicationContext(), ManuActivity.class);
                        context.startActivity(i);
                    }
                });
        builder.create().show();
    }
}
