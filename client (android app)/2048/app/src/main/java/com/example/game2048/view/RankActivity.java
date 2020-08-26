package com.example.game2048.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.game2048.R;
import com.example.game2048.adapter.TopRankArrayAdapter;
import com.example.game2048.api.API;
import com.example.game2048.api.MyRetrofit;
import com.example.game2048.model.MyApplication;
import com.example.game2048.model.TopRank;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends Activity {
    protected ArrayList<TopRank> dataset = new ArrayList<>();
    protected TopRankArrayAdapter adapter;
    protected int highscore;
    protected int rank;
    protected MyApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_ranking);

        getData();
    }

    public void getData(){
        app = (MyApplication)getApplication();
        highscore = app.high_score;
        /*highscore = 23423;
        rank = 1;
        dataset.add(new TopRank(1, "Lê Mậu Toàn",23423));
        dataset.add(new TopRank(2,"Nguyễn Văn A",12233));
        dataset.add(new TopRank(3,"Lê Văn B",11033));*/


        API api = MyRetrofit.getClient().create(API.class);
        JsonObject object = new JsonObject();
        object.addProperty("userToken",app.user_token);
        Call<JsonObject> call = api.getmyrank(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(RankActivity.this, "Have some error on server, please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject rs = response.body();
                rank = rs.get("rank").getAsInt();
                TextView text_high_score = findViewById(R.id.rank_highscore);
                TextView text_rank = findViewById(R.id.rank_rank);
                text_high_score.setText(String.valueOf(highscore));
                text_rank.setText(String.valueOf(rank));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RankActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        call = api.gettoprank();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(RankActivity.this, "Have some error on server, please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject rs = response.body();
                JsonArray arr = rs.get("topRank").getAsJsonArray();
                for (int i = 0; i < arr.size(); i++) {
                    System.out.println(arr.get(i).toString());
                    JsonObject element = arr.get(i).getAsJsonObject();
                    TopRank temp = new TopRank(element.get("rank").getAsInt(),element.get("displayName").getAsString(),element.get("score").getAsInt());
                    dataset.add(temp);
                }

                adapter = new TopRankArrayAdapter(RankActivity.this,0,dataset);
                ListView top_rank = findViewById(R.id.top_rank);
                top_rank.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RankActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void representData(){


    }
}
