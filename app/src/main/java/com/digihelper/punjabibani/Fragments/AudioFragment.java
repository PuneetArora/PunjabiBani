package com.digihelper.punjabibani.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digihelper.punjabibani.Adapters.AudioAdapter;
import com.digihelper.punjabibani.Model.AudioModel;
import com.digihelper.punjabibani.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AudioFragment extends Fragment {
    AudioAdapter adapter;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_audio, container, false);

        MobileAds.initialize(getActivity(), initializationStatus -> {
        });

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Query query = FirebaseFirestore.getInstance()
                .collection("audios");

        FirestoreRecyclerOptions<AudioModel> options = new FirestoreRecyclerOptions.Builder<AudioModel>()
                .setQuery(query, AudioModel.class)
                .build();
        adapter = new AudioAdapter(options, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.audio_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        //adapter.stopListening();
    }


}