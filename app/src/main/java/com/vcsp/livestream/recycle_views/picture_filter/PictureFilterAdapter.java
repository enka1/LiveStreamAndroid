package com.vcsp.livestream.recycle_views.picture_filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;

public class PictureFilterAdapter extends RecyclerView.Adapter<PictureFilterViewHolder> {

    @NonNull
    @Override
    public PictureFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.picture_filter, parent, false);
        return new PictureFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureFilterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
