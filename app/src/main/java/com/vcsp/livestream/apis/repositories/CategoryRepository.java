package com.vcsp.livestream.apis.repositories;

import android.util.Log;

import com.vcsp.livestream.apis.remote.ServiceStore;
import com.vcsp.livestream.apis.response_models.Category;
import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import com.vcsp.livestream.view_models.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryRepository {

    private final static LiveStreamPlatformService service = ServiceStore.getServiceInstance();

    public static void fetchFullCategories(CategoryViewModel model) {
        service.getFullCategories().subscribeOn(Schedulers.io()).subscribe(new Observer<List<Category>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Category> categories) {
                model.setFullCategories(new ArrayList<>(categories));
            }

            @Override
            public void onError(Throwable e) {
                Log.i("API_ERROR", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void fetchFamousCategories(CategoryViewModel model) {
        service.getFamousCategories().subscribeOn(Schedulers.io()).subscribe(new Observer<List<Category>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Category> categories) {
                model.setFamousCategories(new ArrayList<>(categories));
            }

            @Override
            public void onError(Throwable e) {
                Log.i("API_ERROR", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
