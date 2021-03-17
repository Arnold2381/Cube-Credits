package com.example.cubestop;

public class CubeCard {
    String name,desc,img_url;

    public CubeCard(String n,String d,String i){
        name = n;
        desc = d;
        img_url = i;
    }

    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public String getImg_url(){
        return img_url;
    }
}
