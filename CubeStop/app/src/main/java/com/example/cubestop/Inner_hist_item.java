package com.example.cubestop;

public class Inner_hist_item {
    private int product_image;
    private  String product_name;
    private String product_shop;
    private String product_credits;
    public Inner_hist_item(int imageResource, String text1, String text2,String text3)
    {
            product_image=imageResource;
            product_name=text1;
            product_shop=text2;
            product_credits=text3;
    }
    public int getImageResource() {
        return product_image;
    }
    public String getText1() {
        return product_name;
    }
    public String getText2() {
        return product_shop;
    }
    public String getText3() {
        return product_credits;
    }
}
