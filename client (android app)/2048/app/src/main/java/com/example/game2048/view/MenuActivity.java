package com.example.game2048.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.game2048.R;

public class MenuActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button newgame = findViewById(R.id.newgame);
        newgame.setOnClickListener(this);
        Button resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        Button ranking = findViewById(R.id.charts);
        ranking.setOnClickListener(this);
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newgame:
                Intent intent = new Intent(this, GamingActivity.class);
                intent.putExtra("status",0);
                startActivity(intent);
                break;
            case R.id.resume:
                Intent intent1 = new Intent(this, GamingActivity.class);
                intent1.putExtra("status",1);
                startActivity(intent1);
                break;
            case R.id.charts:
                startActivity(new Intent(this, RankActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this,LoginActivity.class));
                deleteData();
                finish();
        }
    }

    public void deleteData(){
        SharedPreferences sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
