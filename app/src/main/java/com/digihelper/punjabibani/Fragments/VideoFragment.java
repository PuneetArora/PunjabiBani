package com.digihelper.punjabibani.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.digihelper.punjabibani.Adapters.VideoAdapter;
import com.digihelper.punjabibani.Model.VideoModel;
import com.digihelper.punjabibani.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class VideoFragment extends Fragment {
    VideoAdapter adapter;


    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        Query query = FirebaseFirestore.getInstance()
                .collection("videos");

        FirestoreRecyclerOptions<VideoModel> options = new FirestoreRecyclerOptions.Builder<VideoModel>()
                .setQuery(query, VideoModel.class)
                .build();
        adapter = new VideoAdapter(options,getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.video_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}