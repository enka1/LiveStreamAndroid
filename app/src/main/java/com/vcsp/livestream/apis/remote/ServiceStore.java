package com.vcsp.livestream.apis.remote;

import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import retrofit2.Retrofit;

public class ServiceStore {
    private static LiveStreamPlatformService service;

    public static LiveStreamPlatformService getServiceInstance() {
        if(service == null){
            Retrofit retrofit = RetrofitClient.getClient();
            service = retrofit.create(LiveStreamPlatformService.class);
            return service;
        }
        return service;
    }
}
