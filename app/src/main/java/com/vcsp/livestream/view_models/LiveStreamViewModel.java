package com.vcsp.livestream.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcsp.livestream.apis.response_models.AvailableLiveStreamsByCategory;

import java.util.ArrayList;

public class LiveStreamViewModel extends ViewModel {
    private MutableLiveData<ArrayList<AvailableLiveStreamsByCategory>> availableLiveStreamsByCategory = new MutableLiveData<>();

    public MutableLiveData<ArrayList<AvailableLiveStreamsByCategory>> getAvailableLiveStreamsByCategory() {
        return availableLiveStreamsByCategory;
    }

    public void setAvailableLiveStreamsByCategory(ArrayList<AvailableLiveStreamsByCategory> availableLiveStreamsByCategory) {
        this.availableLiveStreamsByCategory.postValue(availableLiveStreamsByCategory);
    }
}
