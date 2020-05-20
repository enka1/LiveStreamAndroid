package com.vcsp.livestream.recycle_views.category_full;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.vcsp.livestream.R;

public class CategoryFullViewHolder extends RecyclerView.ViewHolder {

    LinearLayout layout;
    MaterialCardView imgWrapper;
    TextView categoryName;
    ImageView categoryImage;


    public CategoryFullViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.category_full_layout);
        imgWrapper = itemView.findViewById(R.id.img_wrapper);
        categoryName = itemView.findViewById(R.id.category_name);
        categoryImage = itemView.findViewById(R.id.category_image);
    }
}
