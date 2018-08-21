package com.games.tictactoe.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PreMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_main_layout);

        findViewById(R.id.against_pc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreMainActivity.this, MainActivity.class);
                i.putExtra("isAgainstPc",true);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.against_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreMainActivity.this, MainActivity.class);
                i.putExtra("isAgainstPc",false);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.about_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TicTacToeDialog.Builder()
                .addTitle("about app")
                .addData("aslkk asdlkj asd laksd asdlkjas dlaksdj asd alksdj asldk aslkdj aslkdj")
                .show(PreMainActivity.this);
            }
        });

        findViewById(R.id.how_to_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TicTacToeDialog.Builder()
                        .addTitle("how to play")
                        .addData("aslkk asdlkj asd laksd asdlkjas dlaksdj asd alksdj asldk aslkdj aslkdj")
                        .show(PreMainActivity.this);
            }
        });
    }
}
