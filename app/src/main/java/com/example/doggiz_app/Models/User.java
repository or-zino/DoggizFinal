package com.example.doggiz_app.Models;

public class User {

    public String fullName, email, phone, workingPlace, profession, address, instegram, imageName, password;

    public User(){

    }


    public User(String fullName, String email, String phone, String workingPlace, String profession, String address, String instergram, String password){
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.workingPlace = workingPlace;
        this.profession = profession;
        this.address = address;
        this.instegram = instergram;
        this.password = password;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
