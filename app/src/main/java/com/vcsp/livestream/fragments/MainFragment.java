package com.vcsp.livestream.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vcsp.livestream.R;
import com.vcsp.livestream.apis.repositories.CategoryRepository;
import com.vcsp.livestream.apis.repositories.LiveStreamRepository;
import com.vcsp.livestream.recycle_views.StartSnapHelper;
import com.vcsp.livestream.recycle_views.available_live_stream_box.AvailableLiveStreamBoxAdapter;
import com.vcsp.livestream.recycle_views.category_small.CategorySmallAdapter;
import com.vcsp.livestream.room.AppDatabase;
import com.vcsp.livestream.room.DatabaseInstance;
import com.vcsp.livestream.room.entities.User;
import com.vcsp.livestream.view_models.CategoryViewModel;
import com.vcsp.livestream.view_models.LiveStreamViewModel;

import java.util.Objects;


public class MainFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private LiveStreamViewModel liveStreamViewModel;
    private CategorySmallAdapter categorySmallAdapter;
    private RecyclerView categorySmallRecyclerView, availableLiveStreamBoxRecyclerView;
    AvailableLiveStreamBoxAdapter availableLiveStreamBoxAdapter;
    private User user;

    public MainFragment() {
    }

    private void initView(View view) {
        categorySmallRecyclerView = view.findViewById(R.id.category_recycler_view);
        availableLiveStreamBoxRecyclerView = view.findViewById(R.id.available_live_stream_recycler_view);
    }

    private void registerLiveData() {
        categoryViewModel.getFamousCategories().observe(getViewLifecycleOwner(), categories -> {
            categorySmallAdapter.setCategories(categories);
        });
        liveStreamViewModel.getAvailableLiveStreamsByCategory().observe(getViewLifecycleOwner(), availableLiveStreamsByCategories -> {
            availableLiveStreamBoxAdapter.setAvailableLiveStreamsByCategories(availableLiveStreamsByCategories);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AppDatabase appDatabase = DatabaseInstance.getDatabaseInstance(view.getContext());
        user = appDatabase.getUser();
        initView(view);
        setUpSmallCategoryRecyclerView(view);
        setUpAvailableLiveStreamBox(view);
        setUpOnClick(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        liveStreamViewModel = new ViewModelProvider(requireActivity()).get(LiveStreamViewModel.class);
        registerLiveData();
        CategoryRepository.fetchFamousCategories(categoryViewModel);
        LiveStreamRepository.fetchAvailableLiveStreamInAllCategories(liveStreamViewModel);
    }


    private void setUpSmallCategoryRecyclerView(View view) {
        categorySmallAdapter = new CategorySmallAdapter(view);
        categorySmallRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        categorySmallRecyclerView.setAdapter(categorySmallAdapter);
        categorySmallRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildLayoutPosition(view);
                outRect.left = 45;
                outRect.right = 45;
                if (position == 0) {
                    outRect.left = 0;
                }
                if (position == Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1) {
                    outRect.right = 0;
                }
            }
        });
        StartSnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(categorySmallRecyclerView);
    }

    private void setUpAvailableLiveStreamBox(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        availableLiveStreamBoxAdapter = new AvailableLiveStreamBoxAdapter(view, displayMetrics.widthPixels);
        availableLiveStreamBoxRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        availableLiveStreamBoxRecyclerView.setAdapter(availableLiveStreamBoxAdapter);
    }


    private void setUpOnClick(View view) {
        view.findViewById(R.id.all_game_button).setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_fullCategoriesFragment));
    }


//    private void loadMoreBroadcastingContents() {
//        Toasty.normal(getContext(), " Load").show();
//        isBroadcastingContentLoading = true;
//        nestedScrollView.post(() -> {
//            nestedScrollView.fullScroll(View.FOCUS_DOWN);
//        });
//        new Handler().postDelayed(() -> {
//            try {
//                currentBroadcastingContentOffset += 8;
//                BroadcastingRepository.fetchLiveBroadcastingContents(contentsViewModel, currentBroadcastingContentOffset);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, 300);
//    }
//
//
//    private void initLoadMoreOnBroadcastingContentRecyclerView() {
//        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (!nestedScrollView.canScrollVertically(1) && !isBroadcastingContentLoading && scrollY > oldScrollY) {
//                loadMoreBroadcastingContents();
//            }
//        });
//    }

}
