package com.digihelper.punjabibani.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digihelper.punjabibani.Activities.MainActivity;
import com.digihelper.punjabibani.Activities.VideoActivity;
import com.digihelper.punjabibani.Fragments.LiveFragment;
import com.digihelper.punjabibani.Model.VideoModel;
import com.digihelper.punjabibani.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoAdapter extends FirestoreRecyclerAdapter<VideoModel, VideoAdapter.VideoModelHolder> {
    private Context context;

    public VideoAdapter(@NonNull FirestoreRecyclerOptions<VideoModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoModelHolder holder, int position, @NonNull VideoModel model) {
        holder.textViewTitle.setText(model.getTitle());
        Glide.with(context).load(model.getThumbnailUrl()).into(holder.imgButton);
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("ur",model.getVideo_url().toString());
                intent.putExtra("name",model.getTitle().toString());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public VideoModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player,
                parent, false);
        return new VideoModelHolder(v);
    }

    class VideoModelHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageButton imgButton;


        public VideoModelHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            imgButton = itemView.findViewById(R.id.imagebutton_thumbnail);
        }
    }

}
