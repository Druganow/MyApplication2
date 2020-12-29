package com.example.myapplication;

public class AG {
    private String yahr1;
    private String yahr2;
    private String gender;

    public AG(String y1, String y2, String g)
    {
        this.yahr1 = y1;
        this.yahr2 = y2;
        this.gender = g;
    }

    public String getYahr1() {
        return yahr1;
    }

    public String getYahr2() {
        return yahr2;
    }

    public String getGender() {
        return gender;
    }

    public String toString()
    {
        return (Integer.parseInt(gender)==0?"ж":"м")+ " "+yahr1+"-"+ yahr2;
    }
}
