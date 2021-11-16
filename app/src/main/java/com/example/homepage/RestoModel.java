package com.example.homepage;

import io.realm.RealmObject;

public class RestoModel extends RealmObject {

    private String id;
    private String name;
    private String description;
    private String pictureId;
    private String city;

    public RestoModel(){}

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

    public RestoModel(String id, String name, String description, String pictureId, String city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
        this.city = city;
    }

}