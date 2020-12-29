package com.example.myapplication;

public class SM {
    private String name;
    private String yahr;
    private String gender;

    public SM(String n, String y, String g)
    {
        this.name = n;
        this.yahr = y;
        this.gender = g;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return yahr;
    }

    public String getGender() {
        return gender;
    }
}
