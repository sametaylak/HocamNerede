package com.gelecegiyazanlar.hocamnerede.model;

import java.util.Date;

public class LocationPost {

    private String userFullname;
    private String userAvatar;
    private Double userLatitude;
    private Double userLongitude;
    private String userDescription;
    private String userUniversity;
    private long timestamp;

    public LocationPost() {}

    public LocationPost(String userFullname, Double userLatitude, Double userLongitude, String userDescription, String userUniversity, String userAvatar) {
        this.userFullname = userFullname;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.userDescription = userDescription;
        this.userUniversity = userUniversity;
        this.userAvatar = userAvatar;
        this.timestamp = new Date().getTime();
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
