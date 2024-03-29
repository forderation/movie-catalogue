package com.example.moviecatalogue.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.model.TVShow;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TVShowAdapter;

import java.util.ArrayList;


public class TVShowFragment extends Fragment {
    TVShowAdapter tvShowAdapter;
    Context context;
    RecyclerView recyclerView;

    public TVShowFragment() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListTVShows(ArrayList<TVShow> tvShows) {
        if (tvShowAdapter == null) {
            tvShowAdapter = new TVShowAdapter(context);
        }
        tvShowAdapter.setTvShowList(tvShows);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(tvShowAdapter);
    }
}
