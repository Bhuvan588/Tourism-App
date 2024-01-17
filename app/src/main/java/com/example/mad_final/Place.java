package com.example.mad_final;

public class Place {
    String place_name;
    String place_rating;
    String place_status;

    String place_description;
    int place_image;

    public Place(String place_name, String place_rating, String place_status, int image, String place_description) {
        this.place_name = place_name;
        this.place_rating = place_rating;
        this.place_status = place_status;
        this.place_image = image;
        this.place_description= place_description;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_rating() {
        return place_rating;
    }

    public void setPlace_rating(String place_rating) {
        this.place_rating = place_rating;
    }

    public String getPlace_status() {
        return place_status;
    }

    public void setPlace_status(String place_status) {
        this.place_status = place_status;
    }

    public int getImage() {
        return place_image;
    }

    public void setImage(int image) {
        this.place_image = image;
    }

    public String getPlace_description() {
        return place_description;
    }

    public void setPlace_description(String place_description) {
        this.place_description = place_description;
    }
}
