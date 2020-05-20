package com.vcsp.livestream.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "token")
    private String token;
    @ColumnInfo(name = "userId")
    private String userId;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "displayName")
    private String displayName;
    @ColumnInfo(name = "avatarUrl")
    private String avatarUrl;
    @ColumnInfo(name = "streamName")
    private String streamName;
    @ColumnInfo(name = "rtmp")
    private String rtmp;
    @ColumnInfo(name = "rtsp")
    private String rtsp;

    public User(@NonNull String token, String userId, String username, String displayName, String avatarUrl, String streamName, String rtmp, String rtsp) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.streamName = streamName;
        this.rtmp = rtmp;
        this.rtsp = rtsp;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getStreamName() {
        return streamName;
    }

    public String getRtmp() {
        return rtmp;
    }

    public String getRtsp() {
        return rtsp;
    }
}
