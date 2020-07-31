package com.example.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button newgame = findViewById(R.id.newgame);
        newgame.setOnClickListener(this);
        Button resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        Button exit = findViewById(R.id.charts);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()) {
            case R.id.newgame:
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.resume:
                intent.putExtra("status", 1);
                startActivity(intent);
                break;
            case R.id.charts:
                break;
        }
    }
}
