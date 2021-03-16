package com.example.cubestop;

public class Waystop {
    private final double latitude;
    private final double longitude;
    private final String name;
    public Waystop(double lat,double lng,String n){
        latitude = lat;
        longitude = lng;
        name = n;
    }
    public String getName(){
        return name;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
}
