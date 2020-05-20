package com.vcsp.livestream.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vcsp.livestream.R;
import com.vcsp.livestream.recycle_views.live_stream_card.LiveStreamCardAdapter;


public class LiveStreamByCategoryFragment extends Fragment {

    RecyclerView liveStreamRecyclerView;
    LiveStreamCardAdapter liveStreamAdapter;

    public LiveStreamByCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_stream_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String categoryName = LiveStreamByCategoryFragmentArgs.fromBundle(arguments).getCategoryName();
            TextView categoryNameTxt = view.findViewById(R.id.category_name);
            categoryNameTxt.setText(categoryName);
        }
        view.findViewById(R.id.back_button).setOnClickListener(v -> requireActivity().onBackPressed());
        setUpLiveStreamRecyclerView(view);
    }

    private void setUpLiveStreamRecyclerView(View view) {
        liveStreamRecyclerView = view.findViewById(R.id.live_stream_recycler_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        liveStreamAdapter = new LiveStreamCardAdapter(displayMetrics.widthPixels / 2);
        liveStreamRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        liveStreamRecyclerView.setAdapter(liveStreamAdapter);


    }
}
