package com.example.musicca.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.musicca.models.Like;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlaylistAdapter extends RecyclerView.Adapter<CurrentPlaylistAdapter.ViewHolder> {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    public static final String KEY_PLAYLIST = "likedplaylist";
    public static final String KEY_SONG = "likedsong";
    public static final String KEY_USER = "likeduser";

    private static final String TAG = "Queue";

    private Context context;
    private List<String> songObjectIds;
    private String playlistObjectId;

    public CurrentPlaylistAdapter(Context context, List<String> songObjectIds, String playlistObjectId) {
        this.context = context;
        this.songObjectIds = songObjectIds;
        this.playlistObjectId = playlistObjectId;
    }

    @NonNull
    @Override
    public CurrentPlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentPlaylistAdapter.ViewHolder holder, int position) {
        String songObjectId = songObjectIds.get(position);
        //Song song = songs.get(position);
        holder.bind(songObjectId, position);
    }

    @Override
    public int getItemCount() {
        return songObjectIds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivAlbum;
        public TextView tvTitle;
        public TextView tvArtist;
        public ImageView ivLike;
        public TextView tvLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
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

        // this function finds the number of likes of a specific song in a specific playlist
        private int findNumLikes(int position) throws ParseException {
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(KEY_PLAYLIST, playlistObjectId);
            query.whereEqualTo(KEY_SONG, songObjectIds.get(position));
            List<Like> numLikes = query.find();
            return numLikes.size();
        }

        // this function finds a specific song liked by the current user
        // the list can either be empty or have a size of 1, because user cannot like a song more than once
        public List<Like> findLikedByCurrentUser(int position) throws ParseException {
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(KEY_PLAYLIST, playlistObjectId);
            query.whereEqualTo(KEY_SONG, songObjectIds.get(position));
            query.whereEqualTo(KEY_USER, ParseUser.getCurrentUser().getObjectId());
            List<Like> likedByUser = query.find();
            return likedByUser;
        }

        // bind each view of to the song object
        public void bind(String songObjectId, int position) {
            try {
                int num = findNumLikes(position);
                Log.d("SORT SONG", "numlikes " + num);
                tvLikes.setText("" + num);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            try {
                List<Like> likedByUser = findLikedByCurrentUser(position);
                if (likedByUser.size() > 0){
                    ivLike.setImageResource(R.drawable.likefilledicon);
                }
                else {
                    ivLike.setImageResource(R.drawable.likeicon);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ParseQuery<Song> querySong = ParseQuery.getQuery(Song.class);
            // Execute the query to find the object with ID
            querySong.getInBackground(songObjectId, new GetCallback<Song>() {
                @Override
                public void done(Song song, com.parse.ParseException e) {
                    if (e == null) {
                        tvTitle.setText(song.getTitle());
                        tvArtist.setText(song.getArtist());
                        Glide.with(context).load(song.getURL()).into(ivAlbum);
                    } else {
                        Log.d(TAG, "song not found!");
                        Toast.makeText(context, "Error retrieving song!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
