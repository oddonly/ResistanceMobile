package com.ridhofikri.resistancemobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class StartActivity extends Activity implements View.OnClickListener {

    public TextView jumlah;
    public ArrayList<String> roleList = new ArrayList<>();
    public Integer spy;
    public Integer resistance;

    private Player [] playerList;
    private int position = 0;
    private int total = 4;
    private int checkIndex = 0;
    private int playerIndex = 0;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        registerButton = (Button) findViewById(R.id.registerButton);
        jumlah = (TextView) findViewById(R.id.jumlah);
        Bundle bundle = getIntent().getExtras();
        spy = bundle.getInt("Number1");
        resistance = bundle.getInt("Number2");
        total = spy + resistance;
        playerList = new Player[total+1];

        for (int i = 0; i < spy; i++) roleList.add("Spy");

        for (int i = 0; i < resistance; i++) roleList.add("Resistance");

        registerButton.setText("REGISTER PLAYER");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerButton.getText() == "REGISTER PLAYER") {
                    showInputNameDialog();
                } else if (registerButton.getText() == "CHECK TEAMMATES") {
                    checkTeammate();
                } else if (registerButton.getText() == "START GAME") {
                    Intent intent = new Intent(getApplicationContext(), MissionActivity.class);
                    for(Integer i = 0 ; i < total ; i++){
                        intent.putExtra("playerList" + i.toString(), playerList[i]);
                    }
                    intent.putExtra("total", total);
                    startActivity(intent);
                }
            }
        });
    }

    public void showInputNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What is your name?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().length()<2) {
                    Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!roleList.isEmpty()) {
                    position = new Random().nextInt(roleList.size());
                    playerList[playerIndex] = new Player();
                    playerList[playerIndex].setPlayer(input.getText().toString(),roleList.get(position));
                    //playerList[playerIndex].setPlayerName(input.getText().toString());
                    //playerList[playerIndex].setPlayerRole(roleList.get(position));
                    //playerList[playerIndex] = new Player(input.getText().toString(),roleList.get(position));
                    roleList.remove(position);
                } else jumlah.append("Kosong" + "\n");
                if(playerIndex <= total) jumlah.append(playerList[playerIndex].getPlayerName() + " is a " + playerList[playerIndex].getPlayerRole() + "\n");
                else playerIndex--;
                showRoleDialog();
                playerIndex++;
            }
        });
        /*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/
        builder.show();
    }

    public void showRoleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Role");
        builder.setMessage(playerList[playerIndex].getPlayerName() + ", you are " + playerList[playerIndex].getPlayerRole() + ".");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextPerson();
            }
        });
        builder.show();
    }

    public void nextPerson(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Give this device to the next person.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!roleList.isEmpty()){
                    dialog.dismiss();
                } else {
                    startCheckTeammate();
                    registerButton.setText("CHECK TEAMMATES");
                }
            }
        });
        builder.show();
    }

    public void startCheckTeammate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Great, the team has assembled.");
        builder.setMessage("Now, check your teammates.\nStarts from " + playerList[checkIndex].getPlayerName() + ".");

        for(int i = 0 ; i < total ; i++)
            System.out.println("(" + playerList[i].getPlayerName() + "," + playerList[i].getPlayerRole() + ")");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void checkTeammate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check Teammates");

        if(playerList[checkIndex].getPlayerRole().equals("Spy")) {
            builder.setMessage(playerList[checkIndex].getPlayerName() + ", your role is " + playerList[checkIndex].getPlayerRole() + " and your teammate is " + Arrays.toString(spyList(playerList)) + ".");
        }
        else if(playerList[checkIndex].getPlayerRole().equals("Resistance")) {
            builder.setMessage(playerList[checkIndex].getPlayerName() + ", your role is " + playerList[checkIndex].getPlayerRole() + " and you don't know your teammate's roles.");
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextCheck();
            }
        });
        builder.show();
    }

    public void nextCheck(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(playerList[checkIndex+1] != null) builder.setTitle("Give this device to " + playerList[checkIndex+1].getPlayerName() + ".");
        else builder.setTitle("Players registration complete.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkIndex++;
                if(checkIndex < total) {
                    dialog.dismiss();
                } else {
                    registerButton.setText("START GAME");
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    public String [] spyList(Player[] playerList){
        String [] listOfSpyName = new String[spy];
        int spyCount = 0;
        for(int i = 0 ; i < total ; i++){
            if(playerList[i].getPlayerRole().equals("Spy")) {
                listOfSpyName[spyCount] = playerList[i].getPlayerName();
                spyCount++;
            }
        }
        return listOfSpyName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quit Game")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}