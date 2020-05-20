package com.vcsp.livestream.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.remote.ServiceStore;
import com.vcsp.livestream.apis.response_models.LiveStreamDetail;
import com.vcsp.livestream.apis.services.LiveStreamPlatformService;
import com.vcsp.livestream.models.Comment;
import com.vcsp.livestream.recycle_views.watcher_comment.WatcherCommentAdapter;
import com.vcsp.livestream.room.DatabaseInstance;
import com.vcsp.livestream.room.entities.User;
import com.vcsp.livestream.socket.SocketClientRemote;
import com.vcsp.livestream.utils.SocketUtil;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerConfig;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerView;
import com.wowza.gocoder.sdk.api.status.WOWZPlayerStatus;
import com.wowza.gocoder.sdk.api.status.WOWZPlayerStatusCallback;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.wowza.gocoder.sdk.api.configuration.WOWZMediaConfig.FILL_VIEW;


public class LiveStreamRoomFragment extends Fragment implements WOWZPlayerStatusCallback {

    LiveStreamPlatformService service;
    private WOWZPlayerView playerView;
    private WOWZPlayerConfig config;
    private Handler handler;
    private WatcherCommentAdapter roomCommentsAdapter;
    private RecyclerView commentsRecyclerView;
    private Socket socket;
    private User currentUser;
    private String broadcastingId;
    private Emitter.Listener onNewComment = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                Toasty.info(Objects.requireNonNull(getContext()), "Receis").show();
                String userId, username, displayName, avatarUrl, content;
                try {
                    userId = data.getString("userId");
                    username = data.getString("username");
                    displayName = data.getString("displayName");
                    avatarUrl = data.getString("avatarUrl");
                    content = data.getString("content");
                    Comment newComment = new Comment(userId, username, displayName, avatarUrl, content);
                    roomCommentsAdapter.addComment(newComment);
                    roomCommentsAdapter.notifyDataSetChanged();
                    commentsRecyclerView.scrollToPosition(roomCommentsAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    Toasty.error(getContext(), e.getMessage()).show();
                }
            });
        }
    };


    private void initView(View view) {
        playerView = view.findViewById(R.id.video_player);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_content_room, container, false);
        handler = new Handler();
        if (getArguments() != null) {
            LiveStreamRoomFragmentArgs args = LiveStreamRoomFragmentArgs.fromBundle(getArguments());
            broadcastingId = args.getBroadcastingId();
        }
        handler.post(() -> {
            service = ServiceStore.getServiceInstance();
            currentUser = DatabaseInstance.getDatabaseInstance(view.getContext()).getUser();
            initView(view);
            setSendAction(view);
            initRecyclerView(view);
            config = new WOWZPlayerConfig();
            config.setIsPlayback(true);
            config.setStreamName("0I0p2TyswZGNOvFdrbWhbr820y8R5925");
            config.setHostAddress("edge.cdn.wowza.com");
            config.setApplicationName("live");
            config.setPortNumber(1935);
            config.setHLSEnabled(true);
            config.setHLSBackupURL("https://wowzaprod228-i.akamaihd.net/hls/live/1008290/0P0p2TyswZG42ZmJqMVJ2eU1PQmh5837/playlist.m3u8");
            config.setAudioEnabled(true);
            config.setVideoEnabled(true);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            playerView.play(config, this);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        socket = SocketClientRemote.getSocket();
        socket.on("userChat", onNewComment);
//        handler.postDelayed(() -> {
//            service.getLiveStreamDetail(broadcastingId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<LiveStreamDetail>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(LiveStreamDetail liveStreamDetail) {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            requireActivity().runOnUiThread(() -> Toasty.error(requireContext(), e.getMessage() + "").show());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }, 200);
    }

    private void initRecyclerView(View view) {
        commentsRecyclerView = view.findViewById(R.id.comments_recycler_view);
        roomCommentsAdapter = new WatcherCommentAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        commentsRecyclerView.setLayoutManager(layout);
        commentsRecyclerView.setAdapter(roomCommentsAdapter);
    }

    private void setSendAction(View view) {
        EditText commentInput = view.findViewById(R.id.comment_edit_txt);
        commentInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && currentUser != null) {
                String userId = currentUser.getUserId();
                String username = currentUser.getUsername();
                String displayName = currentUser.getDisplayName();
                String avatarUrl = currentUser.getAvatarUrl();
                String content = commentInput.getText().toString();
                Comment newComment = new Comment(userId, username, displayName, avatarUrl, content);
                SocketUtil.chatToRoom(broadcastingId, currentUser, content);
                roomCommentsAdapter.addComment(newComment);
                commentInput.setText("");
                return false;
            } else {
                Toast.makeText(getContext(), "Require Login", Toast.LENGTH_LONG).show();
            }
            return false;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onWZStatus(WOWZPlayerStatus wowzPlayerStatus) {
        switch (wowzPlayerStatus.getState()) {
            case BUFFERING: {
                Toasty.normal(Objects.requireNonNull(getContext()), "Buffering").show();
                break;
            }
            case IDLE: {
                Toasty.normal(Objects.requireNonNull(getContext()), "Idle").show();
                break;
            }
            case CONNECTING: {
                Toasty.normal(Objects.requireNonNull(getContext()), "CONNECTING").show();
                break;
            }
            case PAUSING: {
                Toasty.normal(Objects.requireNonNull(getContext()), "Pausing").show();
                break;
            }
            case PLAYING: {
                Toasty.normal(Objects.requireNonNull(getContext()), "Playing").show();
                break;
            }
            case STOPPING: {
                Toasty.normal(Objects.requireNonNull(getContext()), "Stopping").show();
                break;
            }
        }


    }

    @Override
    public void onWZError(WOWZPlayerStatus wowzPlayerStatus) {
        Toasty.normal(getContext(), "Error").show();
    }

}
