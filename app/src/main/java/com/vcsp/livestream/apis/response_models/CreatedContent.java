package com.vcsp.livestream.apis.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedContent {

    @SerializedName("contentId")
    @Expose
    private String contentId;
    @SerializedName("startedTime")
    @Expose
    private String startedTime;
    @SerializedName("contentTitle")
    @Expose
    private String contentTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("creatorId")
    @Expose
    private String creatorId;
    @SerializedName("playbackUrl")
    @Expose
    private String playbackUrl;
    @SerializedName("endedTime")
    @Expose
    private String endedTime;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public void setPlaybackUrl(String playbackUrl) {
        this.playbackUrl = playbackUrl;
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

}
