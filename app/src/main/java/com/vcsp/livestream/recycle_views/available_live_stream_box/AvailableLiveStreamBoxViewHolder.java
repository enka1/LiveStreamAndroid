package com.vcsp.livestream.recycle_views.available_live_stream_box;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vcsp.livestream.R;

public class AvailableLiveStreamBoxViewHolder extends RecyclerView.ViewHolder {

    RecyclerView liveStreamRecyclerView;
    TextView categoryName;
    View moreButton;

    public AvailableLiveStreamBoxViewHolder(@NonNull View itemView) {
        super(itemView);
        liveStreamRecyclerView = itemView.findViewById(R.id.live_stream_recycler_view);
        categoryName = itemView.findViewById(R.id.category_name);
        moreButton = itemView.findViewById(R.id.more_button);
    }
}
