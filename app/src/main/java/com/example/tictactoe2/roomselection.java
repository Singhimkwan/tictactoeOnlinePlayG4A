package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.net.*;
import java.io.*;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;


public class roomselection extends AppCompatActivity {

    private Socket Socket;


    //private Socket socket;
    {
        try {
            Socket = IO.socket("https://obscure-refuge-45482.herokuapp.com");

        } catch (URISyntaxException e) {}
    }
    String joinid;
    int join=0;
    String username2 ;
    String id;
    String name1;
    String name2;
    int status;

    String youname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Socket.connect();
        setContentView(R.layout.activity_roomselection);

        TextView textViewroom;
        textViewroom = findViewById(R.id.textView2);
        youname= getIntent().getStringExtra("username");
        textViewroom.setText(youname);








        Button buttonroom = findViewById(R.id.create);
        Button buttonjoin = findViewById(R.id.join);
        Button buttonview = findViewById(R.id.view);
        buttonroom.setOnClickListener(new View.OnClickListener() {
            String username = getIntent().getStringExtra("username");
            @Override
            public void onClick(View v) {
                createroom(username);


            }
        });
        buttonview.setOnClickListener(new View.OnClickListener() {
            String username = getIntent().getStringExtra("username");
            @Override
            public void onClick(View v) {
                joinroom(username);


            }
        });

        buttonjoin.setOnClickListener(new View.OnClickListener() {
            String username = getIntent().getStringExtra("username");
            @Override
            public void onClick(View v) {
                joinroom(username);


            }
        });


    }
    private void joinroom(final String username2) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(roomselection.this);
        alertDialog.setTitle("Room ID");
        alertDialog.setMessage("");

        final EditText input = new EditText(roomselection.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Join",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        joinid = input.getText().toString();

                    joinroom2(joinid,username2);
                    }

                });

        alertDialog.setNegativeButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }
    private  void joinroom2(String joinid,String username2)
    {
        Socket.emit("joinroom", joinid, username2);
        Socket.on("joinstatus", onNewMessage);
    }



    private  void showDialog(String tile,String text)
    {
        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);

        MyAlertDialog.setTitle(tile);


        MyAlertDialog.setMessage(text);




        MyAlertDialog.show();
    }
    private void joinroomfail(final String username2) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(roomselection.this);
        alertDialog.setTitle("Room Exist");
        alertDialog.setMessage("Confirm View?");


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        alertDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {



                        Intent intent = new Intent(roomselection.this, OnlineActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name1", name1);
                        intent.putExtra("name2", name2);
                        intent.putExtra("spec", 1);
                        intent.putExtra("youname", youname);
                        startActivity(intent);
                        finish();
                    }

                });

        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }
    private void createroom(String username) {
//

       Socket.emit("createroom", username);
//        Socket.on("roomid", onNewMessage);
        Socket.on("roomid", onNewMessage);

    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try

                    {
                        //data = new JSONObject((String) args[0]);
                        id = args[0].toString();
                        name1 = args[1].toString();
                        name2 = args[2].toString();
                        status = (int)args[3];
                    if (status == 1){
                        Intent intent = new Intent(roomselection.this, OnlineActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name1", name1);
                        intent.putExtra("name2", name2);
                        intent.putExtra("owner", 1);
                        intent.putExtra("youname", youname);
                        startActivity(intent);
                        finish();
                    }else if (status == 0){

                                       showDialog("Create Room Status", "Failed");
                    }else if (status == 4){
                        Intent intent = new Intent(roomselection.this, OnlineActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name1", name1);
                        intent.putExtra("name2", name2);
                        intent.putExtra("youname", youname);
                        startActivity(intent);
                        finish();

                    }else if (status == 3){

                        joinroomfail(username2);

                    }else if (status == 5){

                        showDialog("Join Room Status", "Room ID invalid");

                    }
                    } catch(
                            Exception e)

                    {
                        e.printStackTrace();
                        return;
                    }


                }
            });
        }
    };

}
