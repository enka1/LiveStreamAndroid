package com.vcsp.livestream.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.remote.ServiceStore;
import com.vcsp.livestream.apis.repositories.LiveStreamRepository;
import com.vcsp.livestream.apis.repositories.UserRepository;
import com.vcsp.livestream.apis.response_models.LiveStreamDetail;
import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import com.vcsp.livestream.room.AppDatabase;
import com.vcsp.livestream.room.DatabaseInstance;
import com.vcsp.livestream.room.entities.User;
import com.wowza.gocoder.sdk.api.WowzaGoCoder;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.json.JSONException;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int IMG_REQUEST = 1;


//    private Socket socket;
//
//    private Emitter.Listener onNewContent = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            JSONObject data = (JSONObject) args[0];
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WowzaGoCoder.init(this, "GOSK-8747-010C-10BB-F70A-48A3");
        AppDatabase appDatabase = DatabaseInstance.getDatabaseInstance(this.getApplicationContext());
        User user = appDatabase.getUser();
        if (user == null) {
            try {
                UserRepository.login("dazbee", "nhithanh123", appDatabase);
                Toasty.success(this, "Login!").show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            UserRepository.updateCurrentUserData(user.getToken(), appDatabase);
            Toasty.info(this, "Updated user data").show();
        }
//        socket = SocketClientRemote.getSocket();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.liveBroadcastingFragment:
                case R.id.liveContentRoomFragment: {
                    setUpScreenMode(true);
                    break;
                }
                default:
                    setUpScreenMode(false);
            }
        });
        initOnClick();

    }

    private void setUpScreenMode(boolean isFullScreen) {
        if (isFullScreen) {
            this.findViewById(R.id.bottom_menu).setVisibility(View.GONE);
        } else {
            this.findViewById(R.id.bottom_menu).setVisibility(View.VISIBLE);
        }
    }

    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(picturePath);
            Toasty.success(this, picturePath).show();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part thumbnailImage = MultipartBody.Part.createFormData("thumbnailImage", file.getName(), requestFile);
            LiveStreamPlatformService service = ServiceStore.getServiceInstance();
            service.createNewLiveStream(thumbnailImage).subscribeOn(Schedulers.io()).subscribe(new Observer<LiveStreamDetail>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(LiveStreamDetail liveStreamDetail) {

                }

                @Override
                public void onError(Throwable e) {
                    Log.i("Imagesss", e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private void navigateToBroadcast() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_global_liveBroadcastingFragment);
    }


    private void initOnClick() {
        this.findViewById(R.id.action_button).setOnClickListener(v -> navigateToBroadcast());
    }

}
