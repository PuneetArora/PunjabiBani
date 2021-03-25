package com.digihelper.punjabibani.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digihelper.punjabibani.Model.AudioModel;
import com.digihelper.punjabibani.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ui.PlayerView;

public class AudioAdapter extends FirestoreRecyclerAdapter<AudioModel, AudioAdapter.AudioModelHolder> {
    private final Context context;
    SimpleExoPlayer player;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    AudioManager audioManager;

    public AudioAdapter(@NonNull FirestoreRecyclerOptions<AudioModel> options, Context context) {
        super(options);
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);


    }

    @Override
    protected void onBindViewHolder(@NonNull AudioModelHolder holder, int position, @NonNull AudioModel model) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build();

        holder.textViewTitle.setText(model.getTitle());
        player = new SimpleExoPlayer.Builder(context).build();
        player.setAudioAttributes(audioAttributes, true);
        holder.playerView.setPlayer(player);
        holder.playerView.setShutterBackgroundColor(Color.WHITE);
        MediaItem mediaItem = MediaItem.fromUri(model.getUrl());
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(false);
        player.prepare();

    }

    @NonNull
    @Override
    public AudioModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_player,
                parent, false);
        return new AudioModelHolder(v);
    }

    class AudioModelHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        PlayerView playerView;

        public AudioModelHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_audio);
            playerView = itemView.findViewById(R.id.audioplayer);

        }
    }

}

