package com.example.feedme.models;

import java.util.List;

public class User {

    public String id="";
    public String name="";
    public String image="";
    public List<Recipe> recipes;

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
