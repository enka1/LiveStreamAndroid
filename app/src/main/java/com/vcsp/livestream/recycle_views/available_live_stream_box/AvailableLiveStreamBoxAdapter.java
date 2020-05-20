package com.vcsp.livestream.recycle_views.available_live_stream_box;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.response_models.AvailableLiveStreamsByCategory;
import com.vcsp.livestream.recycle_views.live_stream_card.LiveStreamCardAdapter;

import java.util.ArrayList;

public class AvailableLiveStreamBoxAdapter extends RecyclerView.Adapter<AvailableLiveStreamBoxViewHolder> {

    View rootView;
    int deviceWidth;
    ArrayList<AvailableLiveStreamsByCategory> availableLiveStreamsByCategories;

    public AvailableLiveStreamBoxAdapter(View rootView, int deviceWidth) {
        this.rootView = rootView;
        this.deviceWidth = deviceWidth;
        availableLiveStreamsByCategories = new ArrayList<>();
    }

    public void setAvailableLiveStreamsByCategories(ArrayList<AvailableLiveStreamsByCategory> availableLiveStreamsByCategories) {
        this.availableLiveStreamsByCategories = availableLiveStreamsByCategories;
    }

    @NonNull
    @Override
    public AvailableLiveStreamBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.available_live_stream_box, parent, false);
        return new AvailableLiveStreamBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableLiveStreamBoxViewHolder holder, int position) {
        AvailableLiveStreamsByCategory availableLiveStreamsByCategory = availableLiveStreamsByCategories.get(position);
        LiveStreamCardAdapter liveStreamCardAdapter = new LiveStreamCardAdapter(deviceWidth / 2);
        holder.categoryName.setText(availableLiveStreamsByCategory.getCategoryName());
        holder.liveStreamRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));
        liveStreamCardAdapter.setLiveStreams(new ArrayList<>(availableLiveStreamsByCategory.getLiveStreams()));
        holder.liveStreamRecyclerView.setAdapter(liveStreamCardAdapter);
    }

    @Override
    public int getItemCount() {
        return availableLiveStreamsByCategories.size();
    }
}
