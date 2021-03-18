package com.example.cubestop;

public class CubeCard {
    String name,desc,img_url,stop,type;

    public CubeCard(String n,String d,String i,String stop,String type){
        name = n;
        desc = d;
        img_url = i;
        this.stop = stop;
        this.type = type;
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
    public String getStop(){ return stop; }
    public String getType(){ return type; }
}
