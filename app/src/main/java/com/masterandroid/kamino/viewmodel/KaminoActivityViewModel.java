package com.masterandroid.kamino.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class KaminoActivityViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isImageExpanded = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> showData = new MutableLiveData<>(false);


    public LiveData<Boolean> getIsImageExpanded() {
        return isImageExpanded;
    }


    public LiveData<Boolean> getShowData() {
        return showData;
    }

    public void toggleImageExpanded() {
        Boolean currentExpanded = isImageExpanded.getValue();
        if (currentExpanded != null) {
            isImageExpanded.setValue(!currentExpanded);
        }
    }


    public void toggleShowData() {
        Boolean currentShowData = showData.getValue();
        if (currentShowData != null) {
            showData.setValue(!currentShowData);
        }
    }
}
