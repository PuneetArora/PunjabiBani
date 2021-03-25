package com.digihelper.punjabibani.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.digihelper.punjabibani.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class VideoActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer radioPlayer;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String name;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        playerView = findViewById(R.id.fullscreen_video_view);
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("ur");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer(url);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || radioPlayer == null)) {
            initializePlayer(url);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }

    }


    private void releasePlayer() {
        if (radioPlayer != null) {
            playWhenReady = radioPlayer.getPlayWhenReady();
            playbackPosition = radioPlayer.getCurrentPosition();
            currentWindow = radioPlayer.getCurrentWindowIndex();
            radioPlayer.release();
            radioPlayer = null;
        }
    }

    private void initializePlayer(String uri) {
        radioPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(radioPlayer);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        radioPlayer.setMediaItem(mediaItem);
        radioPlayer.setPlayWhenReady(playWhenReady);
        radioPlayer.seekTo(currentWindow, playbackPosition);
        radioPlayer.prepare();
    }


}