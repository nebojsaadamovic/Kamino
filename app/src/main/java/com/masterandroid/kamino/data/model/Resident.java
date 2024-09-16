package com.masterandroid.kamino.data.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity(tableName = "resident")
public class Resident {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String height;
    private String mass;
    @SerializedName("hair_color")
    @ColumnInfo(name = "hair_color")
    private String hairColor;
    @SerializedName("skin_color")
    @ColumnInfo(name = "skin_color")
    private String skinColor;
    @SerializedName("eye_color")
    @ColumnInfo(name = "eye_color")
    private String eyeColor;
    @SerializedName("birth_year")
    @ColumnInfo(name = "birth_year")
    private String birthYear;
    private String gender;
    @SerializedName("homeworld")
    @ColumnInfo(name = "home_world")
    private String homeWorld;
    private String created;
    private String edited;
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "planet_id")
    private Integer planetId; //FK- this is foreign key for class Planet  --- One Planet have more residents -- for Api not used
    private boolean active;


    public Resident(String name, String height, String mass, String hairColor, String skinColor, String eyeColor, String birthYear, String gender, String homeWorld, String created, String edited, String imageUrl, Integer planetId) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.hairColor = hairColor;
        this.skinColor = skinColor;
        this.eyeColor = eyeColor;
        this.birthYear = birthYear;
        this.gender = gender;
        this.homeWorld = homeWorld;
        this.created = created;
        this.edited = edited;
        this.imageUrl = imageUrl;
        this.planetId = planetId;
    }

    public Resident() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeWorld() {
        return homeWorld;
    }

    public void setHomeWorld(String homeWorld) {
        this.homeWorld = homeWorld;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId = planetId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", mass='" + mass + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", skinColor='" + skinColor + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", gender='" + gender + '\'' +
                ", homeWorld='" + homeWorld + '\'' +
                ", created='" + created + '\'' +
                ", edited='" + edited + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", planetId=" + planetId +
                ", active=" + active +
                '}';
    }
}
