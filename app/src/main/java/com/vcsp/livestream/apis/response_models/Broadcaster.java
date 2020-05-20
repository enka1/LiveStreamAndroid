package com.vcsp.livestream.apis.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broadcaster {

    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("avatarUrl")
    @Expose
    public String avatarUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Broadcaster() {
    }
}