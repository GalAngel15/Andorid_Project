package com.example.cargame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

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

    public void showStartGameDialog(GameManager gameManager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("האם אתה רוצה להתחיל משחק חדש?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "המשחק מתחיל!", Toast.LENGTH_SHORT).show();
                        gameManager.startGame();
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
