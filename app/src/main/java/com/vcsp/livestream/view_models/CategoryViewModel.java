package com.vcsp.livestream.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcsp.livestream.apis.response_models.Category;

import java.util.ArrayList;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Category>> famousCategories = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Category>> fullCategories = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Category>> getFullCategories() {
        return fullCategories;
    }

    public void setFullCategories(ArrayList<Category> fullCategories) {
        this.fullCategories.postValue(fullCategories);
    }

    public MutableLiveData<ArrayList<Category>> getFamousCategories() {
        return famousCategories;
    }

    public void setFamousCategories(ArrayList<Category> famousCategories) {
        this.famousCategories.postValue(famousCategories);
    }
}
