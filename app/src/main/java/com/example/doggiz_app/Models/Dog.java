package com.example.doggiz_app.Models;

public class Dog {

    public String dogName, breed, vet, dateOfBirth, imageName, userEmail;
    public String share;
    public int food, walk;

    public Dog() {

    }


    public Dog(String dogName, String breed, String vet, String dateOfBirth, int food, int walk, String userEmail) {
        this.dogName = dogName;
        this.breed = breed;
        this.vet = vet;
        this.dateOfBirth = dateOfBirth;
        this.food = food;
        this.walk = walk;
        this.userEmail = userEmail;
        this.share = "";
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