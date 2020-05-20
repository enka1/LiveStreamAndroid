package com.vcsp.livestream.models;

public class LiveStream {
    private String broadcasterName;
    private int currentWatcher;
    private String imgUrl;

    public LiveStream(String broadcasterName, int currentWatcher, String imgUrl) {
        this.broadcasterName = broadcasterName;
        this.currentWatcher = currentWatcher;
        this.imgUrl = imgUrl;
    }

    public String getBroadcasterName() {
        return broadcasterName;
    }

    public void setBroadcasterName(String broadcasterName) {
        this.broadcasterName = broadcasterName;
    }

    public int getCurrentWatcher() {
        return currentWatcher;
    }

    public void setCurrentWatcher(int currentWatcher) {
        this.currentWatcher = currentWatcher;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
