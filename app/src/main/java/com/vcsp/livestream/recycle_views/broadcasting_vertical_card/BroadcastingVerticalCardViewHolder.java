package com.vcsp.livestream.recycle_views.broadcasting_vertical_card;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vcsp.livestream.R;

public class BroadcastingVerticalCardViewHolder extends RecyclerView.ViewHolder {

    public ShimmerFrameLayout shimmerFrameLayout;
    public ConstraintLayout contentLayout;
    public TextView broadCasterNameTxt;
    public TextView liveStreamTitleTxt;
    public TextView currentWatchersTextView;
    public ImageView thumbnailImg;

    public BroadcastingVerticalCardViewHolder(@NonNull View itemView) {
        super(itemView);
        shimmerFrameLayout = itemView.findViewById(R.id.live_stream_card_container_placeholder);
        contentLayout = itemView.findViewById(R.id.live_stream_card_container);
        broadCasterNameTxt = itemView.findViewById(R.id.live_stream_card_broadcaster_name_txt);
        liveStreamTitleTxt = itemView.findViewById(R.id.live_stream_card_title);
        currentWatchersTextView = itemView.findViewById(R.id.live_stream_card_watcher_txt);
        thumbnailImg = itemView.findViewById(R.id.live_stream_card_img);
    }
}
