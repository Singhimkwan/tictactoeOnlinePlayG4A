package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe2.ui.login.LoginActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class OnlineActivity extends AppCompatActivity implements View.OnClickListener{
    private com.github.nkzawa.socketio.client.Socket Socket;
    private boolean YouTurn = true;
    private boolean playerturn = false;
    private int roundCount;
    private Button[][] buttons = new Button[3][3];
    TextView textViewme;
    TextView textViewch;
    public String yourname = "";
    public String name1 = "";
    public String name2 = "";
    int YouPoints= 0;
    int ChallengerPoints=0;
    int spec;

    //private Socket socket;
    {
        try {
            Socket = IO.socket("https://obscure-refuge-45482.herokuapp.com");


        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Socket.connect();
        spec = getIntent().getIntExtra("spec", 0);
        if (spec == 1) {
            playerturn = false;
        }

            setContentView(R.layout.activity_online);
            String buttonID = "0";
            //R.id.text_view_p2
        name1 = getIntent().getStringExtra("name1");
        yourname = getIntent().getStringExtra("youname");
        name2 = getIntent().getStringExtra("name2");
            textViewme = findViewById(R.id.text_view_p1);

            textViewme.setText(name1 + "" + ": 0");

            TextView textViewroom;
            textViewroom = findViewById(R.id.button_room);
            String id = getIntent().getStringExtra("id");
            textViewroom.setText("Room" + "" + ":" + id);





            textViewch = findViewById(R.id.text_view_p2);
            textViewch.setText(name2 + "" + ": 0");


            Socket.emit("roomstatus", id);
//try {

            Socket.on("messeger", messeger);

            Socket.on("roomstatus", roomstatus);
            Socket.on("boardstatus", boardstatus);


            int owner = getIntent().getIntExtra("owner", 0);
            if (owner != 1) {
                YouTurn = false;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttonID = "button_" + i + j;
                    int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    buttons[i][j] = findViewById(resID);
                    buttons[i][j].setOnClickListener(this);

                }
            }
        Button buttonroom = findViewById(R.id.button_room);
        buttonroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spec == 1) {
                    back();

                }else
                Socket.emit("dicon", yourname);

            }
        });

            Button button_00 = findViewById(R.id.button_00);
            button_00.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 1, "user1", "O");


                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 1, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });
            Button button_01 = findViewById(R.id.button_01);
            button_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 2, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 2, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });

            Button button_02 = findViewById(R.id.button_02);
            button_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 4, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 4, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });

            Button button_10 = findViewById(R.id.button_10);
            button_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 8, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 8, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });
            Button button_11 = findViewById(R.id.button_11);
            button_11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 16, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 16, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });

            Button button_12 = findViewById(R.id.button_12);
            button_12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 32, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 32, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;

                }
            });

            Button button_20 = findViewById(R.id.button_20);
            button_20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 64, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 64, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });

            Button button_21 = findViewById(R.id.button_21);
            button_21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 128, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 128, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;


                }
            });

            Button button_22 = findViewById(R.id.button_22);
            button_22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerturn == false) {
                        otherturn();
                        return;
                    }
                    if (!((Button) v).getText().toString().equals("")) {
                        return;
                    }

                    if (YouTurn) {
                        ((Button) v).setText("O");
                        Socket.emit("move", 256, "user1", "O");

                    } else {
                        ((Button) v).setText("X");
                        Socket.emit("move", 256, "user2", "X");
                    }
                    playerturn = false;
                    roundCount++;

                }
            });

        }


        public void onClick (View v){



        }
        private Emitter.Listener boardstatus = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int button;
                        int mark;

                        try {
                            //data = new JSONObject((String) args[0]);

                            button = (int) args[0];
                            mark = (int) args[1];


                            if (mark == 1 && button == 1) {
                                Button button_01 = findViewById(R.id.button_00);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 1) {
                                Button button_01 = findViewById(R.id.button_00);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 2) {
                                Button button_01 = findViewById(R.id.button_01);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 2) {
                                Button button_01 = findViewById(R.id.button_01);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 4) {
                                Button button_01 = findViewById(R.id.button_02);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 4) {
                                Button button_01 = findViewById(R.id.button_02);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 8) {
                                Button button_01 = findViewById(R.id.button_10);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 8) {
                                Button button_01 = findViewById(R.id.button_10);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 16) {
                                Button button_01 = findViewById(R.id.button_11);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 16) {
                                Button button_01 = findViewById(R.id.button_11);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 32) {
                                Button button_01 = findViewById(R.id.button_12);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 32) {
                                Button button_01 = findViewById(R.id.button_12);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 64) {
                                Button button_01 = findViewById(R.id.button_20);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 64) {
                                Button button_01 = findViewById(R.id.button_20);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 128) {
                                Button button_01 = findViewById(R.id.button_21);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 128) {
                                Button button_01 = findViewById(R.id.button_21);
                                button_01.setText("X");
                            } else if (mark == 1 && button == 256) {
                                Button button_01 = findViewById(R.id.button_22);
                                button_01.setText("O");
                            } else if (mark == 0 && button == 256) {
                                Button button_01 = findViewById(R.id.button_22);
                                button_01.setText("X");
                            }


                        } catch (
                                Exception e) {
                            e.printStackTrace();
                            return;
                        }


                    }
                });
            }
        };
        private void resetBoard () {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText("");
                }
            }
        }
        private Emitter.Listener roomstatus = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        String name1;
//                        String name2;
                        int status;
                        try {
                            //data = new JSONObject((String) args[0]);

                            name1 = args[0].toString();
                            name2 = args[1].toString();
                            status = (int) args[2];
                            Log.d("updatename", name2);

                            textViewme = findViewById(R.id.text_view_p1);
                            textViewme.setText(name1 + "" + ": 0");

                            textViewch = findViewById(R.id.text_view_p2);
                            textViewch.setText(name2 + "" + ": 0");


                        } catch (
                                Exception e) {
                            e.printStackTrace();
                            return;
                        }

                    }
                });
            }
        };
        private Emitter.Listener messeger = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int value;

                        try {



                            value = (int) args[0];
                            if (value == 1) {
                                //reset
                                resetBoard();
                            } else if (value == 2) {
                                //change move
                                playerturn = true;
                                yourturn();
                            } else if (value == 3) {
                                //player 1 win
                                YouPoints++;
                                textViewme.setText(name1 + "" + ": "+ ""+YouPoints);
                                YouWins();

                            } else  if (value == 4) {
                                //player 2 win
                                ChallengerPoints++;
                                textViewch.setText(name2 + "" + ": "+""+ ChallengerPoints);
                                ChallengerWins();
                            } else  if (value == 5) {
                                //Draw
                                Draw();
                                resetBoard();
                            } else if (value == 6){
                                //Back
                                back();
                                backmessage();
                            }


                        } catch (
                                Exception e) {
                            e.printStackTrace();
                            return;
                        }


                    }
                });


            }
        };
    private void YouWins() {
        showDialog(name1);



    }
    private void ChallengerWins() {
        showDialog(name2);



    }
    private void otherturn() {

        Toast.makeText(this, "You Cant move now!!", Toast.LENGTH_SHORT).show();

    }

    private void yourturn() {

        Toast.makeText(this, "Your turn !!", Toast.LENGTH_SHORT).show();

    }
    private void backmessage() {

        Toast.makeText(this, "Player disconnected !!", Toast.LENGTH_SHORT).show();

    }
    private void Draw() {
        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

        MyAlertDialog.setTitle("Draw !!");


        MyAlertDialog.setMessage("");


        MyAlertDialog.show();


    }

    private void back(){
        Toast.makeText(this, "Back to room selection", Toast.LENGTH_SHORT).show();



        Intent Intent = new Intent(OnlineActivity.this, roomselection.class);
        Intent.putExtra("username", yourname);

        Intent.putExtra("loginstaus", true);

        startActivity(Intent);

        finish();
    }
    private  void showDialog(String text)
    {
        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

        MyAlertDialog.setTitle("Congratulation !!");


        MyAlertDialog.setMessage(text +"" +"  WIN!!");




        MyAlertDialog.show();
    }
}
