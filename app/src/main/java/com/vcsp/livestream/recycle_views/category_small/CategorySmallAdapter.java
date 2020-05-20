package com.vcsp.livestream.recycle_views.category_small;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.response_models.Category;
import com.vcsp.livestream.fragments.MainFragmentDirections;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class CategorySmallAdapter extends RecyclerView.Adapter<CategorySmallViewHolder> {

    ArrayList<Category> categories;
    View rootView;

    public CategorySmallAdapter(View rootView) {
        this.rootView = rootView;
        categories = new ArrayList<>();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategorySmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_small, parent, false);
        return new CategorySmallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySmallViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(holder.categoryImage.getContext()).load(category.getSmallImageUrl()).into(holder.categoryImage);
        holder.categoryImageWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainFragmentDirections.ActionMainFragmentToLiveStreamByCategoryFragment action
                                = MainFragmentDirections.actionMainFragmentToLiveStreamByCategoryFragment(category.getCategoryId(), category.getCategoryName());
                        Navigation.findNavController(rootView).navigate(action);
                    }
                }, 200);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
