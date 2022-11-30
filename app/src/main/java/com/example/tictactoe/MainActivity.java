package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[][] Buttons = new String[3][3];
    private ImageButton[][] imgButtons = new ImageButton[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private LinearLayout linearLayout;
    private Button newGameBtn;
    private ImageView imageViewNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);
        linearLayout = findViewById(R.id.main_linearlayout_lo);
        imageViewNotification = findViewById(R.id.image_view_notification);
        newGameBtn = findViewById(R.id.main_newGame_btn);

        newGameBtn.setVisibility(View.INVISIBLE);
        newGameBtn.setOnClickListener(v->resetBoard());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                imgButtons[i][j] = findViewById(resID);
                imgButtons[i][j].setOnClickListener(this);
                Buttons[i][j] = "empty";
            }
        }
    }

    @Override
    public void onClick(View v) {
        String vId = getResources().getResourceEntryName(v.getId());
        int i = vId.charAt(vId.length()-2)-48;
        int j = vId.charAt(vId.length()-1)-48;
        if (!Buttons[i][j].equals("empty")){
            return;
        }

        if (player1Turn) {
            ((ImageButton) v).setImageResource(R.drawable.x);
            Buttons[i][j] = "x";
        } else {
            ((ImageButton) v).setImageResource(R.drawable.o);
            Buttons[i][j] = "o";
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
            //resetBoard();
        } else if (roundCount == 9) {
            draw();
        } else {
            if (player1Turn) {
                imageViewNotification.setImageResource(R.drawable.oplay);
            } else {
                imageViewNotification.setImageResource(R.drawable.xplay);
            }
            player1Turn = !player1Turn;
        }

    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                imgButtons[i][j].setImageResource(R.drawable.empty);
                Buttons[i][j] = "empty";
            }
        }
        newGameBtn.setVisibility(View.INVISIBLE);
        linearLayout.setForeground(getDrawable(R.drawable.empty));
        imageViewNotification.setImageResource(R.drawable.xplay);
        roundCount = 0;
        player1Turn = true;
    }

    private boolean checkForWin() {
        return true;
    }

    private void player1Wins() {
        player1Points++;
        updatePointsText();
        imageViewNotification.setImageResource(R.drawable.xwin);
        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void player2Wins() {
        player2Points++;
        updatePointsText();
        imageViewNotification.setImageResource(R.drawable.owin);
        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void draw() {
        imageViewNotification.setImageResource(R.drawable.nowin);
        newGameBtn.setVisibility(View.VISIBLE);
    }


}