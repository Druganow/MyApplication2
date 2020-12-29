package com.example.myapplication;

public class Tim {

    public static int Secunds(String str)
    {
        String[] str1 = str.split(":");
        return Integer.parseInt(str1[0])*60 + Integer.parseInt(str1[1]);
    }

    public static String Times(int sec)
    {
        return (sec/60)+":" + (sec%60)+((sec%60)%60==0?"0":"");
    }

    public static String Times(String s)
    {
        int sec = Integer.parseInt(s);
        return (sec/60)+":" + (sec%60)+((sec%60)%60==0?"0":"");
    }
}
