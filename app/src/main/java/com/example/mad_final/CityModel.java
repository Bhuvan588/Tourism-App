package com.example.mad_final;
// CityModel.java
public class CityModel {
    private int cityImage;
    private String cityName;
    private String cityLandmark;
    private String cityAirport;
    private String cityTourists;

    public CityModel(int cityImage, String cityName, String cityLandmark, String cityAirport, String cityTourists) {
        this.cityImage = cityImage;
        this.cityName = cityName;
        this.cityLandmark = cityLandmark;
        this.cityAirport = cityAirport;
        this.cityTourists = cityTourists;
    }

    public int getCityImage() {
        return cityImage;
    }

    public void setCityImage(int cityImage) {
        this.cityImage = cityImage;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityLandmark() {
        return cityLandmark;
    }

    public void setCityLandmark(String cityLandmark) {
        this.cityLandmark = cityLandmark;
    }

    public String getCityAirport() {
        return cityAirport;
    }

    public void setCityAirport(String cityAirport) {
        this.cityAirport = cityAirport;
    }

    public String getCityTourists() {
        return cityTourists;
    }

    public void setCityTourists(String cityTourists) {
        this.cityTourists = cityTourists;
    }
}