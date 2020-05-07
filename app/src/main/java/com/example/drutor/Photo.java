package com.example.drutor;

public class Photo {
    private String tName;
    private String tEmail;
    private String imageUrl;

    public Photo() {
    }

    public Photo(String tName, String tEmail,String imageUrl) {
        this.tName = tName;
        this.tEmail = tEmail;
        this.imageUrl=imageUrl;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }


    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}