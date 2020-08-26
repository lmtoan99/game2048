package com.example.game2048.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @POST("/user/login")
    Call<JsonObject> login(
        @Body JsonObject object
    );

    @POST("/user/register")
    Call<JsonObject> register(
        @Body JsonObject object
    );

    @POST( "/score/newhigh")
    Call<JsonObject> newhigh(
        @Body JsonObject object
    );

    @POST("/score/gethigh")
    Call<JsonObject> gethigh(
        @Body JsonObject object
    );

    @GET("/score/toprank")
    Call<JsonObject> gettoprank();

    @POST("/score/myrank")
    Call<JsonObject> getmyrank(
        @Body JsonObject object
    );
}
