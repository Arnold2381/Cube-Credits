package com.example.cubestop;

public class MenuCard {

    private String name,price,cat,time;
    private boolean isFirst,veg;

    public MenuCard(String n,String p,String cat, String time, boolean frst, boolean veg){
        name = n;
        price = p;
        this.cat = cat;
        this.time = time;
        isFirst = frst;
        this.veg = veg;
    }

    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getCat(){
        return cat;
    }
    public String getTime(){
        return time;
    }
    public boolean getFrst(){
        return isFirst;
    }
    public boolean getVeg(){
        return veg;
    }
}
