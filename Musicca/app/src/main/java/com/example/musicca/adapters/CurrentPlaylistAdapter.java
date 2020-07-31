package com.example.musicca.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.musicca.models.ComparableSong;
import com.example.musicca.models.Like;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.example.musicca.models.SongOnTouchListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<String> sortedSongObjectIds;
    private Context context;
    private List<String> songObjectIds;
    private String playlistObjectId;

    public CurrentPlaylistAdapter(Context context, List<String> songObjectIds, String playlistObjectId) {
        this.context = context;
        this.songObjectIds = songObjectIds;
        this.playlistObjectId = playlistObjectId;
        try {
            this.sortedSongObjectIds = sortSongObjectIds(songObjectIds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<String> sortSongObjectIds(List<String> songObjectIds) throws ParseException {

        ArrayList<ComparableSong> sortedComparableSongs = new ArrayList<>();
        List<String> sortedSongObjectIds = new ArrayList<>();

        for (int i = 0; i < songObjectIds.size(); i++){
            String songObjectId = songObjectIds.get(i);
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(KEY_PLAYLIST, playlistObjectId);
            query.whereEqualTo(KEY_SONG, songObjectId);
            List<Like> matchLikes = query.find();
            sortedComparableSongs.add(new ComparableSong(songObjectId, matchLikes.size()));
        }
        Collections.sort(sortedComparableSongs);
        for (ComparableSong comparableSong: sortedComparableSongs){
            sortedSongObjectIds.add(comparableSong.getSongObjectId());
        }
        notifyDataSetChanged();
        return sortedSongObjectIds;
    }

    @NonNull
    @Override
    public CurrentPlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentPlaylistAdapter.ViewHolder holder, int position) {
        String songObjectId = sortedSongObjectIds.get(position);
        //Song song = songs.get(position);
        holder.bind(songObjectId, position);
    }

    @Override
    public int getItemCount() {
        return sortedSongObjectIds.size();
    }

    // reuploads a list of items -- change to type used
    public void updateSongs() throws ParseException {
        sortedSongObjectIds.clear();
        notifyDataSetChanged();

        List<String> newSortedSongObjIds = sortSongObjectIds(songObjectIds);
        sortedSongObjectIds.addAll(newSortedSongObjIds);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAlbum;
        private TextView tvTitle;
        private TextView tvArtist;
        private ImageView ivLike;
        private TextView tvLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            // Usage: override double tap, single tap and long press
            itemView.setOnTouchListener(new SongOnTouchListener(context) {
                // single tap to select song
                @Override
                public void onSingleTapConfirmed(MotionEvent e) {
                    songSelect(getAdapterPosition());
                }
                // double tap to like or unlike song depending on whether the user has liked the song or not
                @Override
                public void onDoubleTap(MotionEvent e) {
                    try {
                        int position = getAdapterPosition();
                        List<Like> likedByUser = findLikedByCurrentUser(position);
                        // if user has already liked the song, unlike the song
                        // else user hasn't liked the song, so like the song
                        if (likedByUser.size() > 0) {
                            removeLike(likedByUser);
                        } else {
                            addLike(position);
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        Toast.makeText(context, "Error liking song!", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        // on single touch
        // creates a new activity with the selected song, to allow user to play the song
        private void songSelect(int position) {
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                String songObjectId = sortedSongObjectIds.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, SongPlaylistActivity.class);
                ParseQuery<Song> querySong = ParseQuery.getQuery(Song.class);
                querySong.getInBackground(songObjectId, new GetCallback<Song>() {
                    @Override
                    public void done(Song song, com.parse.ParseException e) {
                        if (e == null) {
                            intent.putExtra(EXTRA_ALBUMICONURL, song.getURL());
                            intent.putExtra(EXTRA_SONGTITLE, song.getTitle());
                            intent.putExtra(EXTRA_SONGARTIST, song.getArtist());
                            intent.putExtra(EXTRA_SONGOBJECTID, song.getObjectId());
                            intent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
                            // show the activity
                            context.startActivity(intent);
                            Toast.makeText(context, "Song selected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error showing song!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        // create a new row of like in the Like class on Parse and changes the like image to a filled icon
        public void addLike(int position) throws ParseException {
            Like like = new Like();
            like.setKeySong(sortedSongObjectIds.get(position));
            like.setKeyPlaylist(playlistObjectId);
            like.setKeyUser(ParseUser.getCurrentUser().getObjectId());
            like.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error liking song", e);
                        Toast.makeText(context, "Error liking song!", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        tvLikes.setText("" + findNumLikes(position));
                        ivLike.setImageResource(R.drawable.likefilledicon);
                        Toast.makeText(context, "Song liked!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "song liked", e);
                        notifyDataSetChanged();
                    } catch (ParseException ex) {
                        Toast.makeText(context, "Error liking song!", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                }
            });
        }

        // removes a like once the user unlikes a song by deleting the row of like on parse
        // and changes the like image to a outline icon
        private void removeLike(List<Like> likedByUser) throws ParseException {
                likedByUser.get(0).delete();
                //tvLikes.setText("" + findNumLikes(position));
                ivLike.setImageResource(R.drawable.likeicon);
                Toast.makeText(context, "Song unliked!", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
        }

        // this function finds the number of likes of a specific song in a specific playlist
        private int findNumLikes(int position) throws ParseException {
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(KEY_PLAYLIST, playlistObjectId);
            query.whereEqualTo(KEY_SONG, sortedSongObjectIds.get(position));
            List<Like> numLikes = query.find();
            return numLikes.size();
        }

        // this function finds a specific song liked by the current user
        // the list can either be empty or have a size of 1, because user cannot like a song more than once
        public List<Like> findLikedByCurrentUser(int position) throws ParseException {
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(KEY_PLAYLIST, playlistObjectId);
            query.whereEqualTo(KEY_SONG, sortedSongObjectIds.get(position));
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
                } else {
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
