package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;
import java.io.*;
import java.io.IOException;
import java.net.UnknownHostException;
//import java.io.PrintWriter;
import android.app.Activity;

import android.os.AsyncTask;

import android.os.Bundle;

import android.view.Menu;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import com.example.tictactoe2.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean YouTurn = true;

    private int roundCount;

    private int YouPoints;
    private int ChallengerPoints;

    private TextView textViewYou;
    private TextView textViewChallenger;

        private Socket client;
        private PrintWriter printwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewYou = findViewById(R.id.text_view_p1);
        textViewChallenger = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


        Button buttonroom = findViewById(R.id.button_room);
        buttonroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
                finish();

            }
        });
        Button undo = findViewById(R.id.button_undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetBoard();

            }
        });



    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (YouTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (YouTurn) {
                YouWins();
            } else {
                ChallengerWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            YouTurn = !YouTurn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void YouWins() {
        YouPoints++;
        Toast.makeText(this, "Congratulation !!  You Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void ChallengerWins() {
        ChallengerPoints++;
        Toast.makeText(this, "Challenger Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewYou.setText("You: " + YouPoints);
        textViewChallenger.setText("Challenger: " + ChallengerPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        YouTurn = true;
    }



    private void SendMessage() {

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();


    }

            }

