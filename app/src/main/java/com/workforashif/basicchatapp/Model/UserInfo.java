package com.workforashif.basicchatapp.Model;

public class UserInfo {
    private String name;
    private String status;
    private String image;
    private String thumbnil;
    private boolean online;

    public UserInfo() {
    }

    public UserInfo(String name, String status, String image, String thumbnil) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.thumbnil = thumbnil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnil() {
        return thumbnil;
    }

    public void setThumbnil(String thumbnil) {
        this.thumbnil = thumbnil;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
