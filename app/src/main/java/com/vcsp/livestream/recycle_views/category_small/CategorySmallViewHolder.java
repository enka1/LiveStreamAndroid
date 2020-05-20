package com.vcsp.livestream.recycle_views.category_small;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;

public class CategorySmallViewHolder extends RecyclerView.ViewHolder {
    LinearLayout categoryLayout;
    TextView categoryName;
    ImageView categoryImage;
    View categoryImageWrapper;
    public CategorySmallViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryName = itemView.findViewById(R.id.category_name);
        categoryLayout = itemView.findViewById(R.id.category_small_layout);
        categoryImageWrapper = itemView.findViewById(R.id.category_image_wrapper);
    }
}
