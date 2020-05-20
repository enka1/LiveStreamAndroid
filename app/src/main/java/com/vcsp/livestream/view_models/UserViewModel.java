package com.vcsp.livestream.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vcsp.livestream.apis.response_models.StandardUserData;

public class UserViewModel extends ViewModel {
    private MutableLiveData<StandardUserData> currentUser;

    public MutableLiveData<StandardUserData> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(StandardUserData currentUser) {
        this.currentUser.postValue(currentUser);
    }
}
