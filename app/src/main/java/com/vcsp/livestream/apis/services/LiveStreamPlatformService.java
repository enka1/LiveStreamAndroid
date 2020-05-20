package com.vcsp.livestream.apis.services;

import com.vcsp.livestream.apis.request_model.Login;
import com.vcsp.livestream.apis.response_models.AuthorizedUser;
import com.vcsp.livestream.apis.response_models.AvailableLiveStreamsByCategory;
import com.vcsp.livestream.apis.response_models.Category;
import com.vcsp.livestream.apis.response_models.LiveStreamBroadcasting;
import com.vcsp.livestream.apis.response_models.LiveStreamDetail;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LiveStreamPlatformService {

    @POST("users/login")
    Observable<AuthorizedUser> login(@Body Login loginRequest);

    @GET("users/me")
    Observable<AuthorizedUser> getUserData(@Header("auth") String token);

    @GET("broadcastings/livestream")
    Observable<List<LiveStreamBroadcasting>> getOnLiveStreamBroadcasting(@Query("offset") int offset, @Query("limit") int limit);

    @GET("broadcastings/livestream/{id}")
    Observable<LiveStreamDetail> getLiveStreamDetail(@Path("id") String broadcastingId);

    @Multipart
    @POST("live-streams/test")
    Observable<LiveStreamDetail> createNewLiveStream(@Part MultipartBody.Part thumbnailImage);

    @GET("categories/")
    Observable<List<Category>> getFullCategories();

    @GET("categories/famous")
    Observable<List<Category>> getFamousCategories();

    @GET("categories/available")
    Observable<List<AvailableLiveStreamsByCategory>> getAvailableLiveStreamInAllCategories();
}
