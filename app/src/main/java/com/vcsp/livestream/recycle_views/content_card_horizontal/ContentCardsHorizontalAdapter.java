package com.vcsp.livestream.recycle_views.content_card_horizontal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.response_models.LiveStreamBroadcasting;

import java.util.ArrayList;

public class ContentCardsHorizontalAdapter extends RecyclerView.Adapter<ContentCardsHorizontalViewHolder> {

    private int itemWidth;
    private ArrayList<LiveStreamBroadcasting> contents;

    public ContentCardsHorizontalAdapter(int itemWidth) {
        this.itemWidth = itemWidth;
        contents = new ArrayList<>();
    }

    public void setContents(ArrayList<LiveStreamBroadcasting> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentCardsHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_card_horizontal, parent, false);
        return new ContentCardsHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentCardsHorizontalViewHolder holder, int position) {
        holder.layout.getLayoutParams().width = itemWidth;
        holder.layout.requestLayout();
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
