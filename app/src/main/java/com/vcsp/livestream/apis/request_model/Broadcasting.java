package com.vcsp.livestream.apis.request_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broadcasting {
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("thumbnailUrl")
    @Expose
    public String thumbnailUrl;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;

    public Broadcasting(String title, String description, String thumbnailUrl, String categoryId) {
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.categoryId = categoryId;
    }
}
