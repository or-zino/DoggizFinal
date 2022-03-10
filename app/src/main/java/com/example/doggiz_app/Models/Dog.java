package com.example.doggiz_app.Models;

public class Dog {

    public String dogName, breed, vet, dateOfBirth, imageName, userEmail,ownerName;
    public String share;
    public int maxFood, maxWalk, currentFood, currentWalk;

    public Dog() {

    }


    public Dog(String dogName, String breed, String vet, String dateOfBirth, FoodAndWalks foodAndWalks, String userEmail,String ownerName) {
        this.dogName      = dogName;
        this.breed        = breed;
        this.vet          = vet;
        this.dateOfBirth  = dateOfBirth;
        this.userEmail    = userEmail;
        this.share        = "";
        this.ownerName    = ownerName;
        this.maxFood      = foodAndWalks.maxFood;
        this.maxWalk      = foodAndWalks.maxWalk;
        this.currentFood  = foodAndWalks.currentFood;
        this.currentWalk  = foodAndWalks.currentWalks;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setShare(String share) {
        if(this.share.equals(""))
            this.share = this.share + share;
        this.share = this.share + "," + share;
    }
}