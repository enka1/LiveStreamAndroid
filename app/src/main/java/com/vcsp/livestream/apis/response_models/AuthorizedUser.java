package com.vcsp.livestream.apis.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizedUser {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("avatarUrl")
    @Expose
    public String avatarUrl;
    @SerializedName("streamName")
    @Expose
    public String streamName;
    @SerializedName("rtmp")
    @Expose
    public String rtmp;
    @SerializedName("rtsp")
    @Expose
    public String rtsp;

}
