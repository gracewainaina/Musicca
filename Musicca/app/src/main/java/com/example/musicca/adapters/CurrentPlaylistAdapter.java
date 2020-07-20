package com.example.musicca.adapters;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> Searchbar onquery listener text
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
import com.example.musicca.activities.SongQueueActivity;
<<<<<<< HEAD
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
=======
import com.example.musicca.models.Song;
>>>>>>> Searchbar onquery listener text

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class CurrentPlaylistAdapter extends RecyclerView.Adapter<CurrentPlaylistAdapter.ViewHolder> {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    private static final String TAG = "Queue";

    private Context context;
    private List<String> songObjectIds;
    private String playlistObjectId;

    public CurrentPlaylistAdapter(Context context, List<String> songObjectIds, String playlistObjectId) {
        this.context = context;
        this.songObjectIds = songObjectIds;
=======
public class CurrentPlaylistAdapter extends RecyclerView.Adapter<CurrentPlaylistAdapter.ViewHolder>{

    private Context context;
    private List<Song> songs;
    private String playlistObjectId;

    public CurrentPlaylistAdapter(Context context, List<Song> songs, String playlistObjectId) {
        this.context = context;
        this.songs = songs;
>>>>>>> Searchbar onquery listener text
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
<<<<<<< HEAD
        String songObjectId = songObjectIds.get(position);
        //Song song = songs.get(position);
        holder.bind(songObjectId);
=======
        Song song = songs.get(position);
        holder.bind(song);
>>>>>>> Searchbar onquery listener text
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return songObjectIds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
=======
        return songs.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
>>>>>>> Searchbar onquery listener text
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
<<<<<<< HEAD
                String songObjectId = songObjectIds.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, SongPlaylistActivity.class);
                ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
                // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
                query.getInBackground(songObjectId, new GetCallback<Song>() {
                    @Override
                    public void done(Song song, com.parse.ParseException e) {
                        if (e == null) {
                            Log.d(TAG, "song found22" + song.getTitle());
                            intent.putExtra(EXTRA_ALBUMICONURL, song.getURL());
                            intent.putExtra(EXTRA_SONGTITLE, song.getTitle());
                            intent.putExtra(EXTRA_SONGARTIST, song.getArtist());
                            intent.putExtra(EXTRA_SONGOBJECTID, song.getObjectId());
                            intent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
                            // show the activity
                            context.startActivity(intent);
                            Toast.makeText(context, "Song select", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "song not found22!");
                        }
                    }
                });
            }
        }

        public void bind(String songObjectId) {
            ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
            // Execute the query to find the object with ID
            query.getInBackground(songObjectId, new GetCallback<Song>() {
                @Override
                public void done(Song song, com.parse.ParseException e) {
                    if (e == null) {
                        Log.d(TAG, "song found11" + song.getTitle());
                        tvTitle.setText(song.getTitle());
                        tvArtist.setText(song.getArtist());
                        Glide.with(context).load(song.getURL()).into(ivAlbum);
                    } else {
                        Log.d(TAG, "song not found11!");
                    }
                }
            });
=======
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
>>>>>>> Searchbar onquery listener text
        }
    }
}
