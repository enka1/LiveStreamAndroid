package com.vcsp.livestream.recycle_views.broadcasting_vertical_card;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vcsp.livestream.NavGraphDirections;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.response_models.LiveStreamBroadcasting;
import com.vcsp.livestream.fragments.MainFragmentDirections;
import com.vcsp.livestream.room.entities.User;
import com.vcsp.livestream.utils.SocketUtil;

import java.util.ArrayList;

public class BroadcastingVerticalCardAdapter extends RecyclerView.Adapter<BroadcastingVerticalCardViewHolder> {

    private ArrayList<LiveStreamBroadcasting> broadcastings;
    private User userData;

    public BroadcastingVerticalCardAdapter(User userData) {
        this.userData = userData;
        broadcastings = new ArrayList<>();
    }

    public void setBroadcastings(ArrayList<LiveStreamBroadcasting> broadcastings) {
        this.broadcastings = broadcastings;
    }

    @NonNull
    @Override
    public BroadcastingVerticalCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_card_vertical, parent, false);
        return new BroadcastingVerticalCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BroadcastingVerticalCardViewHolder holder, int position) {
        LiveStreamBroadcasting broadcasting = broadcastings.get(position);
        holder.broadCasterNameTxt.setText(broadcasting.getBroadcaster().getDisplayName());
        holder.liveStreamTitleTxt.setText(broadcasting.getTitle());
        Glide.with(holder.thumbnailImg.getContext())
                .load(broadcasting.thumbnailUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.shimmerFrameLayout.stopShimmer();
                holder.shimmerFrameLayout.setVisibility(View.GONE);
                holder.contentLayout.setVisibility(View.VISIBLE);
                return false;
            }
        })
                .into(holder.thumbnailImg);
        holder.contentLayout.setOnClickListener(v -> {
            SocketUtil.joinChatRoom(broadcasting.broadcastingId, userData);
            NavGraphDirections.ActionGlobalLiveContentRoomFragment action = NavGraphDirections.actionGlobalLiveContentRoomFragment(broadcasting.getBroadcastingId());
            Navigation.findNavController(v).navigate(action);
        });
    }


    @Override
    public int getItemCount() {
        return broadcastings.size();
    }
}
