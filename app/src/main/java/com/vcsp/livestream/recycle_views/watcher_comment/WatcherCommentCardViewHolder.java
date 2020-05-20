package com.vcsp.livestream.recycle_views.watcher_comment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class WatcherCommentCardViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView userAvatarImg;
    public TextView username;
    public TextView content;
    public WatcherCommentCardViewHolder(@NonNull View itemView) {
        super(itemView);
        userAvatarImg = itemView.findViewById(R.id.user_avatar_img);
        username = itemView.findViewById(R.id.username);
        content = itemView.findViewById(R.id.comment_content);
    }
}
