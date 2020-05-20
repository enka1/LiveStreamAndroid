package com.vcsp.livestream.recycle_views.live_stream_card;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.response_models.LiveStream;

import java.util.ArrayList;

public class LiveStreamCardAdapter extends RecyclerView.Adapter<LiveStreamCardViewHolder> {

    private int width;
    private ArrayList<LiveStream> liveStreams;

    public LiveStreamCardAdapter(int width) {
        this.width = width;
        liveStreams = new ArrayList<>();
    }

    public void setLiveStreams(ArrayList<LiveStream> liveStreams) {
        this.liveStreams = liveStreams;
    }

    @NonNull
    @Override
    public LiveStreamCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.live_stream_card_pc, parent, false);
        return new LiveStreamCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveStreamCardViewHolder holder, int position) {
        LiveStream liveStream = liveStreams.get(position);
        holder.liveStreamCardImg.getLayoutParams().height = width * 9 / 16;
        holder.liveStreamCardLayout.getLayoutParams().width = width - 15;
        holder.liveStreamCardLayout.requestLayout();
        holder.liveStreamCardImg.requestLayout();
        holder.streamerName.setText(liveStream.getStreamer().getDisplayName());
        holder.liveStreamTitle.setText(liveStream.getTitle());
        Glide.with(holder.liveStreamCardImg.getContext()).load(liveStream.getThumbnailUrl()).into(holder.liveStreamCardImg);
    }

    @Override
    public int getItemCount() {
        return liveStreams.size();
    }
}
