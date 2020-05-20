package com.vcsp.livestream.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.socketio.client.Socket;
import com.pedro.encoder.input.video.CameraHelper;
import com.pedro.rtplibrary.rtsp.RtspCamera2;
import com.pedro.rtplibrary.view.OpenGlView;
import com.pedro.rtsp.utils.ConnectCheckerRtsp;
import com.vcsp.livestream.R;
import com.vcsp.livestream.recycle_views.StartSnapHelper;
import com.vcsp.livestream.recycle_views.picture_filter.PictureFilterAdapter;
import com.vcsp.livestream.room.AppDatabase;
import com.vcsp.livestream.room.DatabaseInstance;
import com.vcsp.livestream.room.entities.User;
import com.vcsp.livestream.socket.SocketClientRemote;
import com.vcsp.livestream.utils.SocketUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class LiveStreamFragment extends Fragment implements ConnectCheckerRtsp, SurfaceHolder.Callback {

    private final int IMG_REQUEST = 1;
    private final String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private MultipartBody.Part thumbnailImage;
    private AppDatabase appDatabase;
    private Socket socket;
    private User user;
    private String broadcastingId;
    private RtspCamera2 rtspCamera2;
    private OpenGlView openGlView;
    private EditText broadcastingTitleEditText, broadcastingDescriptionEditText;
    private Handler handler;
    private Animation slideInBottom, slideOutBottom, popIn, popOut;
    private View settingBox, backgroundView, pictureFilterBox, subCameraSwitchingButton,
            subPictureFilterButton, subQualitySettingButton, thumbnailSelectBox;
    private ImageView thumbnailImageView;
    private boolean isPictureFilterOpen = false, isGrantedPermission = false, isBroadcastSettingMenuOpen = false,
            isThumbnailSelectBoxOpen = false;

    private boolean hasPermissions() {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(requireContext(), permission)) {
                isGrantedPermission = false;
                return false;
            }
        }
        isGrantedPermission = true;
        return true;
    }

    private void askedPermissionIfNotGranted() {
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 1);
        }
    }

    private void initView(View view) {
        openGlView = view.findViewById(R.id.camera_view);
        backgroundView = view.findViewById(R.id.background_view);
        settingBox = view.findViewById(R.id.setting_box);
        pictureFilterBox = view.findViewById(R.id.picture_filter_box);
        broadcastingTitleEditText = view.findViewById(R.id.broadcasting_title);
        broadcastingDescriptionEditText = view.findViewById(R.id.broadcasting_description);
        subCameraSwitchingButton = view.findViewById(R.id.sub_camera_switch_button);
        subPictureFilterButton = view.findViewById(R.id.sub_picture_filter_button);
        subQualitySettingButton = view.findViewById(R.id.sub_setting_button);
        thumbnailSelectBox = view.findViewById(R.id.thumbnailSelectBox);
        thumbnailImageView = view.findViewById(R.id.thumbnailImage);
    }

    private void prepareBeforeLiveStream() {
        rtspCamera2 = new RtspCamera2(openGlView, this);
        rtspCamera2.setVideoBitrateOnFly(2500);
        rtspCamera2.setLimitFPSOnFly(30);
        openGlView.getHolder().addCallback(this);
    }


    private void setUpPreviewFullScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ConstraintLayout.LayoutParams openGlViewLayoutParams = (ConstraintLayout.LayoutParams) openGlView.getLayoutParams();
        openGlViewLayoutParams.width = displayMetrics.heightPixels * 11 / 16;
        openGlView.setLayoutParams(openGlViewLayoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_stream, container, false);
        initView(view);
        handler = new Handler();
        handler.postDelayed(() -> {
            socket = SocketClientRemote.getSocket();
            initSocketListener();
            appDatabase = DatabaseInstance.getDatabaseInstance(view.getContext());
            user = appDatabase.getUser();
            askedPermissionIfNotGranted();
        }, 300);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler.postDelayed(() -> {
            initButtonClick(view);
            prepareBeforeLiveStream();
            setUpPreviewFullScreen();
            initPictureFilter(view);
            initAnimation();
        }, 500);
    }

    private void startNewLiveStream() {
        Context context = requireContext();
        if (rtspCamera2.prepareAudio() && rtspCamera2.prepareVideo()) {
            if (user != null) {
                Toasty.info(context, "Create new broadcasting!").show();
                String title = broadcastingTitleEditText.getText().toString();
                String description = broadcastingDescriptionEditText.getText().toString();

            } else {
                Toasty.error(context, "User is null!").show();
            }
        } else {
            Toasty.error(context, "Device failed to encoded!").show();
        }
    }

    private void stopLiveStream() {
        try {
            SocketUtil.stopBroadcasting(user.getToken(), broadcastingId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuccessRtsp() {
        requireActivity().runOnUiThread(() -> Toasty.info(Objects.requireNonNull(getContext()), "Success").show());
    }

    @Override
    public void onConnectionFailedRtsp(String reason) {
        Toasty.info(Objects.requireNonNull(getContext()), reason).show();
    }

    @Override
    public void onNewBitrateRtsp(long bitrate) {

    }

    @Override
    public void onDisconnectRtsp() {
        requireActivity().runOnUiThread(() -> Toasty.info(Objects.requireNonNull(getContext()), "Disconnect").show());
    }

    @Override
    public void onAuthErrorRtsp() {

    }

    @Override
    public void onAuthSuccessRtsp() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (isGrantedPermission) {
            rtspCamera2.startPreview(CameraHelper.Facing.FRONT);
        }
        askedPermissionIfNotGranted();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (rtspCamera2.isRecording()) {
            rtspCamera2.stopRecord();
        }
        if (rtspCamera2.isStreaming()) {
            rtspCamera2.stopStream();
        }
        rtspCamera2.stopPreview();
    }

    private void openSettingBox() {
        settingBox.setVisibility(View.VISIBLE);
        backgroundView.setVisibility(View.VISIBLE);
        settingBox.startAnimation(popIn);
    }

    private void closeSettingBox() {
        settingBox.startAnimation(popOut);
        handler.postDelayed(() -> {
            settingBox.setVisibility(View.GONE);
            backgroundView.setVisibility(View.GONE);
        }, 150);
    }

    private void openPictureFilterBox() {
        isPictureFilterOpen = true;
        pictureFilterBox.setVisibility(View.VISIBLE);
        pictureFilterBox.startAnimation(slideInBottom);
        pictureFilterBox.requestFocus();
    }

    private void closePictureFilterBox() {
        isPictureFilterOpen = false;
        pictureFilterBox.startAnimation(slideOutBottom);
        handler.postDelayed(() -> {
            pictureFilterBox.setVisibility(View.GONE);
        }, 200);
    }

    private void openThumbnailSelectBox() {
        isThumbnailSelectBoxOpen = true;
        thumbnailSelectBox.setVisibility(View.VISIBLE);
        thumbnailSelectBox.startAnimation(slideInBottom);
        thumbnailSelectBox.requestFocus();
    }

    private void closeThumbnailSelectBox() {
        isThumbnailSelectBoxOpen = false;
        thumbnailSelectBox.startAnimation(slideOutBottom);
        handler.postDelayed(() -> thumbnailSelectBox.setVisibility(View.GONE), 200);
    }

    private void initAnimation() {
        slideInBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom_small);
        slideOutBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_bottom_small);
        popIn = AnimationUtils.loadAnimation(requireContext(), R.anim.pop_in_small);
        popOut = AnimationUtils.loadAnimation(requireContext(), R.anim.pop_out_small);
    }

    private void initButtonClick(View view) {
        view.findViewById(R.id.finish_button).setOnClickListener(v -> stopLiveStream());
        view.findViewById(R.id.picture_filter_button).setOnClickListener(v -> openPictureFilterBox());
        view.findViewById(R.id.setting_button).setOnClickListener(v -> openSettingBox());
        view.findViewById(R.id.setting_confirm_button).setOnClickListener(v -> closeSettingBox());
        view.findViewById(R.id.setting_close_button).setOnClickListener(v -> closeSettingBox());
        backgroundView.setOnClickListener(v -> closeSettingBox());
        openGlView.setOnClickListener(v -> {
            if (isPictureFilterOpen) {
                pictureFilterBox.clearFocus();
            }
            if (isThumbnailSelectBoxOpen) {
                thumbnailSelectBox.clearFocus();
            }
        });

        thumbnailSelectBox.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                closeThumbnailSelectBox();
            }
        });

        pictureFilterBox.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                closePictureFilterBox();
            }
        });
        view.findViewById(R.id.camera_switch_button).setOnClickListener(v -> rtspCamera2.switchCamera());
        view.findViewById(R.id.broadcast_button).setOnClickListener(v -> startNewLiveStream());
        view.findViewById(R.id.broadcast_menu_button).setOnClickListener(v -> {
            if (isBroadcastSettingMenuOpen) {
                closeMenu();
            } else {
                openMenu();
            }
        });
        view.findViewById(R.id.selectThumbnailButton).setOnClickListener(v -> openThumbnailSelectBox());
        subPictureFilterButton.setOnClickListener(v -> {
            openPictureFilterBox();
            closeMenu();
        });
        subCameraSwitchingButton.setOnClickListener(v -> {
            closeMenu();
            rtspCamera2.switchCamera();
        });
        subQualitySettingButton.setOnClickListener(v -> {
            closeMenu();
            openSettingBox();
        });
        view.findViewById(R.id.closeThumbnailChooseButton).setOnClickListener(v -> {
            thumbnailSelectBox.clearFocus();
        });

        view.findViewById(R.id.openGalleryButton).setOnClickListener(v -> chooseThumbnailImageFromGallery());

        view.findViewById(R.id.takePictureButton).setOnClickListener(v -> chooseThumbnailImageByTakeAPhoto());
    }

    private void openMenu() {
        isBroadcastSettingMenuOpen = true;
        subCameraSwitchingButton.setVisibility(View.VISIBLE);
        subQualitySettingButton.setVisibility(View.VISIBLE);
        subPictureFilterButton.setVisibility(View.VISIBLE);
        subCameraSwitchingButton.animate().alpha(1).translationX(-200).setDuration(250).start();
        subQualitySettingButton.animate().alpha(1).translationY(200).setDuration(250).start();
        subPictureFilterButton.animate().alpha(1).translationY(400).setDuration(350).start();
    }

    private void closeMenu() {
        isBroadcastSettingMenuOpen = false;
        subCameraSwitchingButton.animate().alpha(0).translationX(0).setDuration(250).withEndAction(() ->
                subCameraSwitchingButton.setVisibility(View.GONE)).start();
        subQualitySettingButton.animate().alpha(0).translationY(0).setDuration(250).withEndAction(() ->
                subQualitySettingButton.setVisibility(View.GONE)).start();
        subPictureFilterButton.animate().alpha(0).translationY(0).setDuration(350).withEndAction(() -> {
            subPictureFilterButton.setVisibility(View.GONE);
        }).start();
    }

    private void initPictureFilter(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.picture_filter_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        LinearSnapHelper snapHelper = new StartSnapHelper();
        PictureFilterAdapter adapter = new PictureFilterAdapter();
        recyclerView.setAdapter(adapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private void initSocketListener() {
        socket.on(SocketUtil.CREATE_NEW_BROADCASTING_SUCCESS, args -> {
            JSONObject data = (JSONObject) args[0];
            requireActivity().runOnUiThread(() -> {
                Toasty.success(getContext(), "SUCCESS").show();
                try {
                    setUpBroadcastMode();
                    broadcastingId = data.getString("broadcastingId");
                    Toasty.success(getContext(), broadcastingId).show();
                    rtspCamera2.startStream(user.getRtsp());
                } catch (JSONException e) {
                    Toasty.error(getContext(), e.getMessage() + "errorr").show();
                    e.printStackTrace();
                }
            });
        });
        socket.on(SocketUtil.STOP_BROADCASTING_SUCCESS, args -> {
            requireActivity().onBackPressed();
        });
    }

    private void setUpBroadcastMode() {
        View view = requireView();
        View topBarPre = view.findViewById(R.id.top_bar_pre_broadcasting);
        View topBarOn = view.findViewById(R.id.top_bar_on_broadcasting);
        View editWrapper = view.findViewById(R.id.broadcasting_edit_wrapper);
        View broadcastButton = view.findViewById(R.id.broadcast_button);
        View thumbnailSelectWrapper = view.findViewById(R.id.thumbnail_select_wrapper);
        View broadcastingSetUpMenu = view.findViewById(R.id.broadcast_menu_button);
        thumbnailSelectWrapper.animate().alpha(0).translationY(1000).setDuration(250).withEndAction(() -> thumbnailSelectWrapper.setVisibility(View.GONE)).start();
        topBarPre.animate().alpha(0).setDuration(250).withEndAction(() -> {
            topBarPre.setVisibility(View.GONE);
            topBarOn.setVisibility(View.VISIBLE);
            broadcastingSetUpMenu.setVisibility(View.VISIBLE);
            topBarOn.animate().alpha(1).setDuration(250).start();
            broadcastingSetUpMenu.animate().alpha(1).setDuration(250).start();
        }).start();
        view.findViewById(R.id.finish_button).setVisibility(View.VISIBLE);
        view.findViewById(R.id.setting_button).setVisibility(View.GONE);
        view.findViewById(R.id.picture_filter_button).setVisibility(View.GONE);
        editWrapper.animate().alpha(0).setDuration(250).withEndAction(() -> editWrapper.setVisibility(View.GONE)).start();
        broadcastButton.animate().alpha(0).translationX(-1000).setDuration(250).withEndAction(() -> broadcastButton.setVisibility(View.GONE)).start();
    }

    private void chooseThumbnailImageFromGallery() {
        thumbnailSelectBox.clearFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        }, 250);
    }

    private void chooseThumbnailImageByTakeAPhoto() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = requireContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(picturePath);
            Toasty.success(this.getContext(), picturePath).show();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            thumbnailImage = MultipartBody.Part.createFormData("thumbnailImage", file.getName(), requestFile);
            try {
                thumbnailImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
