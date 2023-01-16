package com.example.feedme.models;

import android.widget.ImageView;

public class FeedModel {
    ImageView feedImage, profilePics;
    String feedTitle, StringBody;


    public void setFeedImage(ImageView feedImage) {
        this.feedImage = feedImage;
    }

    public void setProfilePics(ImageView profilePics) {
        this.profilePics = profilePics;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public void setStringBody(String stringBody) {
        StringBody = stringBody;
    }

    public ImageView getFeedImage() {
        return feedImage;
    }

    public ImageView getProfilePics() {
        return profilePics;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public String getStringBody() {
        return StringBody;
    }

    public FeedModel(ImageView feedImage, ImageView profilePics, String feedTitle, String stringBody) {
        this.feedImage = feedImage;
        this.profilePics = profilePics;
        this.feedTitle = feedTitle;
        StringBody = stringBody;
    }
}
