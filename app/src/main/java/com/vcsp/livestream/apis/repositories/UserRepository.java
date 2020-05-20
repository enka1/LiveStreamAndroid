package com.vcsp.livestream.apis.repositories;

import com.vcsp.livestream.apis.remote.ServiceStore;
import com.vcsp.livestream.apis.request_model.Login;
import com.vcsp.livestream.apis.response_models.AuthorizedUser;
import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import com.vcsp.livestream.room.AppDatabase;
import com.vcsp.livestream.room.entities.User;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRepository {
    private final static LiveStreamPlatformService service = ServiceStore.getServiceInstance();

    public static void login(String username, String password, AppDatabase database) throws JSONException {
        Login loginRequest = new Login(username, password);
        service.login(loginRequest).subscribeOn(Schedulers.io()).subscribe(new Observer<AuthorizedUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AuthorizedUser authorizedUser) {
                database.addUser(new User(
                        authorizedUser.token,
                        authorizedUser.userId,
                        authorizedUser.username,
                        authorizedUser.displayName,
                        authorizedUser.avatarUrl,
                        authorizedUser.streamName,
                        authorizedUser.rtmp,
                        authorizedUser.rtsp
                ));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void updateCurrentUserData(String token, AppDatabase database) {
        service.getUserData(token).subscribeOn(Schedulers.io()).subscribe(new Observer<AuthorizedUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AuthorizedUser userData) {
                database.addUser(new User(
                        userData.token,
                        userData.userId,
                        userData.username,
                        userData.displayName,
                        userData.avatarUrl,
                        userData.streamName,
                        userData.rtmp,
                        userData.rtsp
                ));
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
