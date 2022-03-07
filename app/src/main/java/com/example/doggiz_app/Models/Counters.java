package com.example.doggiz_app.Models;

import android.widget.Button;

public class Counters {

    String dogName;
    int foodCounter;
    int walkCounter;
    Button foodM, foodP, walkM, walkP;


    public Counters(){
        this.dogName ="";
        this.foodCounter = 0;
        this.walkCounter = 0;
        this.foodM = null;
        this.foodP = null;
        this.walkM = null;
        this.walkP = null;
    }

    public Counters(String dogName,int foodCounter, int walkCounter){

        this.dogName     = dogName;
        this.foodCounter = foodCounter;
        this.walkCounter = walkCounter;

    }

    public int getFoodCounter() {
        return foodCounter;
    }

    public int getWalkCounter() {
        return walkCounter;
    }

    public String getDogName() {
        return dogName;
    }

    public Button getFoodM() {
        return foodM;
    }

    public Button getFoodP() {
        return foodP;
    }

    public Button getWalkM() {
        return walkM;
    }

    public Button getWalkP() {
        return walkP;
    }


    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setFoodCounter(int foodCounter) {
        this.foodCounter = foodCounter;
    }

    public void setWalkCounter(int walkCounter) {
        this.walkCounter = walkCounter;
    }

    public void setFoodM(Button foodM) {
        this.foodM = foodM;
    }

    public void setFoodP(Button foodP) {
        this.foodP = foodP;
    }

    public void setWalkM(Button walkM) {
        this.walkM = walkM;
    }

    public void setWalkP(Button walkP) {
        this.walkP = walkP;
    }
}
