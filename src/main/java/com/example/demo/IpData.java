package com.example.demo;

public class IpData {
    private String ip;
    private String City;
    private String latitude;
    private String longitude;
    private String country_name;
    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
    public String getCountry_name() {
        return country_name;
    }
    public IpData(String country_name) {
        this.country_name = country_name;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String langitude) {
        this.longitude = langitude;
    }
    public IpData() {
    }
    public IpData(String ip, String city, String latitude, String langitude) {
        this.ip = ip;
        City = city;
        this.latitude = latitude;
        this.longitude = langitude;
    }
}
