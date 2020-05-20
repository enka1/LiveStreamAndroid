package com.vcsp.livestream.apis.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveStreamDetail {
    @SerializedName("broadcastingId")
    @Expose
    public String broadcastingId;
    @SerializedName("broadcasterId")
    @Expose
    public String broadcasterId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
    @SerializedName("thumbnailUrl")
    @Expose
    public String thumbnailUrl;
    @SerializedName("startedTime")
    @Expose
    public String startedTime;
    @SerializedName("endedTime")
    @Expose
    public Object endedTime;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("streamName")
    @Expose
    public String streamName;
    @SerializedName("hlsUrl")
    @Expose
    public String hlsUrl;
    @SerializedName("isAlreadyFollowed")
    @Expose
    public boolean isAlreadyFollowed;
}
