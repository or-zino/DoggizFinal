package com.example.doggiz_app.Models;

public class PreviousDay {

    String   value;
    int day, month;

    public PreviousDay(){};

    public PreviousDay(String value, int month, int day){

        this.value = value;
        this.month = month;
        this.day   = day;

    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getValue() {
        return value;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }
}
