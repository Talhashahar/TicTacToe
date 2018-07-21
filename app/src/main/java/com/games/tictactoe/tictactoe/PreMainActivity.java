package com.games.tictactoe.tictactoe;

import android.app.Activity;
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
    }
}
