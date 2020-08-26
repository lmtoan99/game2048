package com.example.game2048.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.game2048.R;
import com.example.game2048.api.API;
import com.example.game2048.api.MyRetrofit;
import com.example.game2048.model.MyApplication;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getData();
    }

    public void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
        final String user_token = sharedPreferences.getString("userToken",null);
        if (user_token!=null) {
            API api = MyRetrofit.getClient().create(API.class);
            JsonObject object = new JsonObject();
            object.addProperty("userToken",user_token);
            Call<JsonObject> call = api.gethigh(object);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!response.isSuccessful()){
                        startActivity(new Intent(Splash.this,LoginActivity.class));
                        finish();
                    }
                    MyApplication app = (MyApplication) getApplication();
                    app.user_token = user_token;
                    app.high_score = response.body().get("score").getAsInt();
                    Intent intent = new Intent(Splash.this,MenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Splash.this, "Have some error", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
