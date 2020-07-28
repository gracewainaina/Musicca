package com.example.musicca.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.activities.SongPlaylistActivity;
import com.example.musicca.models.Song;

import java.util.List;

public class CurrentPlaylistAdapter extends RecyclerView.Adapter<CurrentPlaylistAdapter.ViewHolder>{

    private Context context;
    private List<Song> songs;
    private String playlistObjectId;

    public CurrentPlaylistAdapter(Context context, List<Song> songs, String playlistObjectId) {
        this.context = context;
        this.songs = songs;
        this.playlistObjectId = playlistObjectId;
    }

    @NonNull
    @Override
    public CurrentPlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_queue_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentPlaylistAdapter.ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivAlbum;
        TextView tvTitle;
        TextView tvArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the post at the position, this won't work if the class is static
                Song song = songs.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, SongPlaylistActivity.class);
                // serialize the post using parceler, use its short name as a key
                intent.putExtra("albumiconurl", song.getURL());
                intent.putExtra("songtitle", song.getTitle());
                intent.putExtra("songartist", song.getArtist());
                intent.putExtra("songObjectid", song.getObjectId());
                intent.putExtra("playlistobjectid", playlistObjectId);
                // show the activity
                context.startActivity(intent);
                Toast.makeText(context, "Song select", Toast.LENGTH_SHORT).show();
            }
        }

        public void bind(Song song) {
            tvTitle.setText(song.getTitle());
            tvArtist.setText(song.getArtist());
            Glide.with(context).load(song.getURL()).into(ivAlbum);
        }
    }
}
