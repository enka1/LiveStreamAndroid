package com.vcsp.livestream.recycle_views.content_card_horizontal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;

public class ContentCardsHorizontalViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout layout;
    public ImageView thumbnailImg, avatarImg;
    public TextView broadcasterNameTxt;

    public ContentCardsHorizontalViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.live_stream_card_horizontal_layout);
        thumbnailImg = itemView.findViewById(R.id.live_stream_card_content_thumbnail_img);
        avatarImg = itemView.findViewById(R.id.live_stream_card_broadcaster_avatar);
        broadcasterNameTxt = itemView.findViewById(R.id.live_stream_card_broadcaster_name_txt);
    }
}
