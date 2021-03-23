package com.digihelper.punjabibani.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digihelper.punjabibani.Utils.FullScreenHelper;
import com.digihelper.punjabibani.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class LiveFragment extends Fragment {
    private PlayerView playerView;
    private SimpleExoPlayer radioPlayer;
    private PlaybackStateListener playbackStateListener;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer yTPlayer;
    private FullScreenHelper fullScreenHelper;

    public LiveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        playerView = view.findViewById(R.id.video_view);
        fullScreenHelper = new FullScreenHelper(getActivity(),playerView,container.findViewById(R.id.tabs));
        playbackStateListener = new PlaybackStateListener();
         youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "s-Gcicxi-HE";
                yTPlayer = youTubePlayer;
                yTPlayer.cueVideo(videoId, 0);
            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);

                if (state == PlayerConstants.PlayerState.PLAYING) {

                    if (radioPlayer.isPlaying()) {
                        radioPlayer.pause();

                    }

                }
            }});

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                fullScreenHelper.enterFullScreen();
                //hideSystemUi();   //Don't use this method it hides status bar permanently
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                fullScreenHelper.exitFullScreen();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT < 24 || radioPlayer == null)) {
            initializePlayer();
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
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
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
            radioPlayer.removeListener(playbackStateListener);
            radioPlayer.release();
            radioPlayer = null;
        }
    }

    private void initializePlayer() {
        radioPlayer  = new SimpleExoPlayer.Builder(getActivity()).build();
        playerView.setPlayer(radioPlayer);
        MediaItem mediaItem = MediaItem.fromUri("https://gurbanikirtan.radioca.st/start.mp3");
        radioPlayer.setMediaItem(mediaItem);

        radioPlayer.setPlayWhenReady(playWhenReady);
        radioPlayer.seekTo(currentWindow, playbackPosition);
        radioPlayer.addListener(playbackStateListener);
        radioPlayer.prepare();
    }

    private class PlaybackStateListener implements Player.EventListener {
        @Override
        public void onIsPlayingChanged(boolean isPlaying)
        {
            if(isPlaying) {
               if(yTPlayer!=null )
                yTPlayer.pause();
            }
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            if(radioPlayer.isPlaying())
                youTubePlayerView.setVisibility(View.GONE);
                else{
            fullScreenHelper.enterFullScreen();
            youTubePlayerView.enterFullScreen();
            }

        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
           fullScreenHelper.exitFullScreen();
            youTubePlayerView.setVisibility(View.VISIBLE);
           youTubePlayerView.exitFullScreen();

        }
    }
}



