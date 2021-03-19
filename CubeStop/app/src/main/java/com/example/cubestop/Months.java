package com.example.cubestop;

public class Months {
    private final String month;
    private final String credits;
    private final String service;
    private final double price;
    private final String shop;
    private final String timestamp;
    public Months(String month1,String service1,String credits1,double price1,String shop1,String timestamp1){
        service=service1;
        credits=credits1;
        price=price1;
        shop=shop1;
        timestamp=timestamp1;
        month=month1;

    }
    public String getService(){
        return service;
    }
    public String getCredits(){
        return credits;
    }
    public double getPrice(){
        return price;
    }
    public String getShop(){return shop;}
    public String getTimestamp(){return timestamp;}

    public String getMonth() {
        return month;
    }
}
