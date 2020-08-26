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

public class LoginActivity extends Activity {
    private EditText username;
    private EditText password;
    private Button login;
    private TextView login_register;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();

        setListener();
    }

    protected void getView(){
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login);
        login_register = findViewById(R.id.login_register_link);
    }

    protected void setListener(){
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginListener();
            }
        });

        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRegisterListener();
            }
        });
    }

    protected void loginListener(){
        API api = MyRetrofit.getClient().create(API.class);

        if (username.getText().length()==0 || password.getText().length() == 0){
            Toast.makeText(this, "Username and password can not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject object = new JsonObject();
        object.addProperty("username",username.getText().toString());
        object.addProperty("password",password.getText().toString());

        Call<JsonObject> call = api.login(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Username and password are wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject rs = response.body();
                MyApplication app = (MyApplication) getApplication();
                app.user_token = rs.get("userToken").getAsString();
                app.high_score = rs.get("score").getAsInt();
                Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userToken",app.user_token);
                editor.apply();

                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void loginRegisterListener(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
