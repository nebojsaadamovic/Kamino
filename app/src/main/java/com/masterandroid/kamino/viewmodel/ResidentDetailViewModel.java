package com.masterandroid.kamino.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResidentDetailViewModel extends ViewModel {

    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> height = new MutableLiveData<>();
    private final MutableLiveData<String> hairColor = new MutableLiveData<>();
    private final MutableLiveData<String> skinColor = new MutableLiveData<>();
    private final MutableLiveData<String> eyeColor = new MutableLiveData<>();
    private final MutableLiveData<String> birthDay = new MutableLiveData<>();
    private final MutableLiveData<String> gender = new MutableLiveData<>();
    private final MutableLiveData<String> imageUrl = new MutableLiveData<>();

    public void setResidentDetails(String name, String height, String hairColor, String skinColor,
                                   String eyeColor, String birthDay, String gender, String imageUrl) {
        this.name.setValue(name);
        this.height.setValue(height);
        this.hairColor.setValue(hairColor);
        this.skinColor.setValue(skinColor);
        this.eyeColor.setValue(eyeColor);
        this.birthDay.setValue(birthDay);
        this.gender.setValue(gender);
        this.imageUrl.setValue(imageUrl);
    }

    public LiveData<String> getName() { return name; }
    public LiveData<String> getHeight() { return height; }
    public LiveData<String> getHairColor() { return hairColor; }
    public LiveData<String> getSkinColor() { return skinColor; }
    public LiveData<String> getEyeColor() { return eyeColor; }
    public LiveData<String> getBirthDay() { return birthDay; }
    public LiveData<String> getGender() { return gender; }
    public LiveData<String> getImageUrl() { return imageUrl; }
}
