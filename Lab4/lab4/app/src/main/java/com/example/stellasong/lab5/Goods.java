package com.example.stellasong.lab5;

/**
 * Created by StellaSong on 2017/10/21.
 */

public class Goods {
    private String name;
    private String info;
    private String price;
    private Object tag;

    public Goods(String name, String info, String price, Object tag){
        this.name = name;
        this.info = info;
        this.price = price;
        this.tag = tag;
    }

    public String getName(){
        return name;
    }
    public String getInfo(){
        return info;
    }
    public String getprice(){
        return price;
    }
    public Object getTag() {return tag;}
}
