package com.vcsp.livestream.recycle_views.category_full;

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
import com.vcsp.livestream.fragments.FullCategoriesFragmentDirections;

import java.util.ArrayList;

public class CategoryFullAdapter extends RecyclerView.Adapter<CategoryFullViewHolder> {

    int width;

    private View rootView;

    private ArrayList<Category> categories;

    public CategoryFullAdapter(int width, View rootView) {
        this.width = width;
        this.rootView = rootView;
        categories = new ArrayList<>();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryFullViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_full, parent, false);
        return new CategoryFullViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFullViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.layout.getLayoutParams().width = width;
        holder.imgWrapper.getLayoutParams().height = width * 4 / 3;
        holder.layout.requestLayout();
        holder.imgWrapper.requestLayout();
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(holder.categoryImage.getContext()).load(category.getImageUrl()).into(holder.categoryImage);
        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(() -> {
                    FullCategoriesFragmentDirections.ActionFullCategoriesFragmentToLiveStreamByCategoryFragment action =
                            FullCategoriesFragmentDirections.actionFullCategoriesFragmentToLiveStreamByCategoryFragment(category.getCategoryId(), category.getCategoryName());
                    Navigation.findNavController(rootView).navigate(action);
                }, 200);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
