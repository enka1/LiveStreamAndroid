package com.vcsp.livestream.apis.repositories;

import com.vcsp.livestream.apis.remote.ServiceStore;
import com.vcsp.livestream.apis.response_models.AvailableLiveStreamsByCategory;
import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import com.vcsp.livestream.view_models.LiveStreamViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LiveStreamRepository {

    private final static LiveStreamPlatformService service = ServiceStore.getServiceInstance();

    public static void fetchAvailableLiveStreamInAllCategories(LiveStreamViewModel model) {
        service.getAvailableLiveStreamInAllCategories().subscribeOn(Schedulers.io()).subscribe(new Observer<List<AvailableLiveStreamsByCategory>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<AvailableLiveStreamsByCategory> availableLiveStreamsByCategories) {
                model.setAvailableLiveStreamsByCategory(new ArrayList<>(availableLiveStreamsByCategories));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
