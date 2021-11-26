package com.example.homepage;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RestoModel extends RealmObject {

    private String id;
    private String name;
    private String description;
    private String pictureId;
    private String city;
    private String address;
    RealmList<String> categories;
    RealmList<String> foods;
    RealmList<String> drinks;

    public RestoModel(){}

    public RestoModel(String id, String name, String description, String city, String address, String pictureId, RealmList<String> categoriesString, RealmList<String> foodsString, RealmList<String> drinksString) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.address = address;
        this.pictureId = pictureId;
        this.categories = categoriesString;
        this.foods = foodsString;
        this.drinks = drinksString;
    }

    public RestoModel(String id, String name, String description, String pictureId, String city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.pictureId = pictureId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RealmList<String> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<String> categories) {
        this.categories = categories;
    }

    public RealmList<String> getFoods() {
        return foods;
    }

    public void setFoods(RealmList<String> foods) {
        this.foods = foods;
    }

    public RealmList<String> getDrinks() {
        return drinks;
    }

    public void setDrinks(RealmList<String> drinks) {
        this.drinks = drinks;
    }

//    public RestoModel(String id, String name, String description, String pictureId, String city) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.pictureId = pictureId;
//        this.city = city;
//    }
//    public RestoModel(String id, String name, String description, String pictureId, String city, String address, RealmList<String> categories, RealmList<String> foods, RealmList<String> drinks) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.pictureId = pictureId;
//        this.city = city;
//        this.address = address;
////        this.rating = rating;
//        this.categories = categories;
//        this.foods = foods;
//        this.drinks = drinks;
//    }
}