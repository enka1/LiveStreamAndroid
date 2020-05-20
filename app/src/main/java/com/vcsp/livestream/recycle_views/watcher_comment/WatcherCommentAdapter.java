package com.vcsp.livestream.recycle_views.watcher_comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vcsp.livestream.R;
import com.vcsp.livestream.models.Comment;

import java.util.ArrayList;

public class WatcherCommentAdapter extends RecyclerView.Adapter<WatcherCommentCardViewHolder> {

    private ArrayList<Comment> comments;

    public WatcherCommentAdapter() {
        comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @NonNull
    @Override
    public WatcherCommentCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_card, parent, false);
        return new WatcherCommentCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatcherCommentCardViewHolder holder, int position) {
        Comment comment = comments.get(position);
        Glide.with(holder.userAvatarImg.getContext()).load(comment.getAvatarUrl()).into(holder.userAvatarImg);
        holder.username.setText(comment.getDisplayName());
        holder.content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
