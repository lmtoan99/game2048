package com.example.game2048.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisterActivity extends Activity {
    private EditText username;
    private EditText password;
    private EditText displayname;
    private Button register;
    private TextView register_login;
    private MyApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_register);

        app = (MyApplication) getApplication();

        getView();

        setListener();
    }

    protected void getView(){
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        displayname = findViewById(R.id.register_displayname);
        register = findViewById(R.id.register);
        register_login = findViewById(R.id.register_login_link);
    }

    protected void setListener(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegisterLogin();
            }
        });
    }

    protected void onClickRegister(){
        if (username.getText().length()==0 || password.getText().length()==0||displayname.getText().length()==0){
            Toast.makeText(this, "All fields can not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        API api = MyRetrofit.getClient().create(API.class);
        JsonObject object = new JsonObject();
        object.addProperty("username",username.getText().toString());
        object.addProperty("password",password.getText().toString());
        object.addProperty("display_name",displayname.getText().toString());
        Call<JsonObject> call = api.register(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code()==200){
                    app.user_token = response.body().get("userToken").getAsString();
                    app.high_score = response.body().get("score").getAsInt();
                    startActivity(new Intent(RegisterActivity.this,MenuActivity.class));

                    SharedPreferences sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userToken",app.user_token);
                    editor.apply();

                    finish();
                }else if (response.code()==201){
                    Toast.makeText(RegisterActivity.this, "Username created.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Have some error, please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Have some error, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onClickRegisterLogin(){
        finish();
    }
}
