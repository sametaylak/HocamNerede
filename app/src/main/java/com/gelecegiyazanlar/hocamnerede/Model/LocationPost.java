package com.gelecegiyazanlar.hocamnerede.Model;

public class LocationPost {

    private String userUid;

    private Double userLongitude;
    private String userDescription;
    private String userUniversity;
    public LocationPost() {}

    public LocationPost(String userUid, Double userLatitude, Double userLongitude, String userDescription, String userUniversity) {
        this.userUid = userUid;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.userDescription = userDescription;
        this.userUniversity = userUniversity;
    }

    private Double userLatitude;
    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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
}
