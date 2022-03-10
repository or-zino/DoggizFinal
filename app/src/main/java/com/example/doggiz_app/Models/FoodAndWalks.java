package com.example.doggiz_app.Models;

public class FoodAndWalks {

    int maxFood;
    int maxWalk;
    int currentFood;
    int currentWalks;

    public FoodAndWalks(){

    }

    public FoodAndWalks(int maxFood, int currentFood, int maxWalk, int currentWalks){
        this.maxFood      = maxFood;
        this.currentFood  = currentFood;
        this.maxWalk      = maxWalk;
        this.currentWalks = currentWalks;
    }


}
