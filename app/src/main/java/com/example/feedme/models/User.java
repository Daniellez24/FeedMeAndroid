package com.example.feedme.models;

public class User {

    public String id="";
    public String name="";
    public String image="";
    public String[] recipes;

    public User(){

    }

    public User(String id, String name, String image, String[] recipes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.recipes = recipes;
    }
}
