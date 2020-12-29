package com.example.myapplication;

public class SM1 {
    private String id;
    private String name;
    public SM1(String i, String n)
    {
        this.id=i;
        this.name = n;
    }
    public String toID()
    {
        return id;
    }
    public String toString()
    {
        return name;
    }
}
