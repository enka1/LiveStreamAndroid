package com.vcsp.livestream.apis.request_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
