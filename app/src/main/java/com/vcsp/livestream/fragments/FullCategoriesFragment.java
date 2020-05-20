package com.vcsp.livestream.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.repositories.CategoryRepository;
import com.vcsp.livestream.recycle_views.category_full.CategoryFullAdapter;
import com.vcsp.livestream.view_models.CategoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullCategoriesFragment extends Fragment {

    Handler handler = new Handler();

    private CategoryViewModel categoryViewModel;
    private CategoryFullAdapter categoryFullAdapter;
    private RecyclerView categoryRecyclerView;

    private void registerLiveData() {
        categoryViewModel.getFullCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryFullAdapter.setCategories(categories);
            categoryFullAdapter.notifyDataSetChanged();
        });
    }


    public FullCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_full_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOnClick(view);
        initRecyclerView(view);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        registerLiveData();
        handler.postDelayed(() -> CategoryRepository.fetchFullCategories(categoryViewModel), 200);
    }

    private void initRecyclerView(View view) {
        RecyclerView categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        categoryFullAdapter = new CategoryFullAdapter(displayMetrics.widthPixels / 3, view);
        categoryRecyclerView.setAdapter(categoryFullAdapter);
    }


    private void initOnClick(View view) {
        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}
