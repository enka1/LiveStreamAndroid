package com.vcsp.livestream.recycle_views.live_stream_card;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vcsp.livestream.R;

public class LiveStreamCardViewHolder extends RecyclerView.ViewHolder {

    View liveStreamCardLayout;
    ImageView liveStreamCardImg;
    TextView liveStreamTitle, streamerName;

    public LiveStreamCardViewHolder(@NonNull View itemView) {
        super(itemView);
        liveStreamCardLayout = itemView.findViewById(R.id.liveStreamLayout);
        liveStreamCardImg = itemView.findViewById(R.id.liveStreamImg);
        liveStreamTitle = itemView.findViewById(R.id.liveS_stream_title);
        streamerName = itemView.findViewById(R.id.streamer_name);
    }
}
