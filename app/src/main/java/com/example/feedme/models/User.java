package com.example.feedme.models;

import java.util.List;

public class User {

    public String id="";
    public String name="";
    public String image="";
    public List recipes;

    public User(){

    }

    public User(String id, String name, String image, List recipes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.recipes = recipes;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
