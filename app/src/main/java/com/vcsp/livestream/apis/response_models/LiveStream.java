package com.vcsp.livestream.apis.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveStream {

    @SerializedName("liveStreamId")
    @Expose
    private String liveStreamId;
    @SerializedName("streamerId")
    @Expose
    private String streamerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("startedTime")
    @Expose
    private String startedTime;
    @SerializedName("endedTime")
    @Expose
    private Object endedTime;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("streamer")
    @Expose
    private Streamer streamer;

    public String getLiveStreamId() {
        return liveStreamId;
    }

    public void setLiveStreamId(String liveStreamId) {
        this.liveStreamId = liveStreamId;
    }

    public String getStreamerId() {
        return streamerId;
    }

    public void setStreamerId(String streamerId) {
        this.streamerId = streamerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public Object getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(Object endedTime) {
        this.endedTime = endedTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public void setStreamer(Streamer streamer) {
        this.streamer = streamer;
    }

}