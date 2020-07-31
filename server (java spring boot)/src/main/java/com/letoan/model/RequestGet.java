package com.letoan.model;

public class RequestGet {
    String userToken;

    public RequestGet() {
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public RequestGet(String userToken) {
        this.userToken = userToken;
    }
}
