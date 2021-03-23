package com.digihelper.punjabibani.Model;

public class VideoModel {
    private String title;
    private String thumbnailUrl;
    private String video_url;


    public VideoModel() {
    }

    public VideoModel(String title, String thumbnailUrl, String video_url) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.video_url = video_url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }


}
