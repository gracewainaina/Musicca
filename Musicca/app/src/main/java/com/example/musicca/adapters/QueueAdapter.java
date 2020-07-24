package com.example.musicca.adapters;
<<<<<<< HEAD

import android.content.Context;
import android.content.Intent;
import android.util.Log;
=======
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> Searchbar onquery listener text
=======
=======
=======
>>>>>>> Building search adapter and queue adapter
>>>>>>> Building search adapter and queue adapter
=======
>>>>>>> attempt to resolve conflict with master branch
import android.util.Log;
>>>>>>> Search view functionality completed
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.activities.SongQueueActivity;
import com.example.musicca.models.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> implements Filterable {
<<<<<<< HEAD
<<<<<<< HEAD

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    private static final String TAG = "QueueAdapter";

=======
>>>>>>> Searchbar onquery listener text
=======
    private static final String TAG = "QueueAdapter";
>>>>>>> Search view functionality completed
    private Context context;
    private List<Song> songs;
    private List<Song> songsAll;
    private String playlistObjectId;

    // Creates the adapter for holding playlist
    public QueueAdapter(Context context, List<Song> songs, String playlistObjectId) {
        this.context = context;
        this.songs = songs;
        this.songsAll = new ArrayList<>(songs);
        this.playlistObjectId = playlistObjectId;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Search view functionality completed
        Log.d(TAG, "length of this.songsAll " + this.songsAll.size());
        Log.d(TAG, "length of this.songs " + this.songs.size());
        Log.d(TAG, "length of songs " + songs.size());
        Log.d("PLAYLIST OBJECT ID", "playlistObjectId" + playlistObjectId);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Searchbar onquery listener text
=======
>>>>>>> Search view functionality completed
=======
=======
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.musicca.activities.PartyActivity;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {
    private Context mContext;
    private PartyActivity mPartyActivity;
    private Playlist mPlaylist;
    private List<Song> mSongs;

    // Creates the adapter for holding playlist
    public QueueAdapter(Context context, List<Song> songs, PartyActivity partyActivity, Playlist playlist) {
        mContext = context;
        mPartyActivity = partyActivity;
        mSongs = new ArrayList<>(songs);
        mPlaylist = playlist;
>>>>>>> Building search adapter and queue adapter
>>>>>>> Building search adapter and queue adapter
=======
>>>>>>> attempt to resolve conflict with master branch
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_queue_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            // run background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Song> filteredList = new ArrayList<>();
                // if string is empty, return the entire list or 'charSequence.toString().isEmpty()'
<<<<<<< HEAD
<<<<<<< HEAD
                if (charSequence == null || charSequence.length() == 0) {
                    Log.d(TAG, "nothing typed yet");
                    filteredList.addAll(songsAll);
                } else {
                    Log.d(TAG, "check if song found");
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    Log.d(TAG, "length of songsAll " + songsAll.size() + filterPattern);
                    for (Song song : songsAll) {
                        if (song.getTitle().toLowerCase().contains(filterPattern)) {
                            Log.d(TAG, "song found");
=======
                if (charSequence.toString().isEmpty()) {
=======
                if (charSequence == null || charSequence.length() == 0) {
                    Log.d(TAG, "nothing typed yet");
>>>>>>> Search view functionality completed
                    filteredList.addAll(songsAll);
                }
                else {
                    Log.d(TAG, "check if song found");
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    Log.d(TAG, "length of songsAll " + songsAll.size() + filterPattern);
                    for (Song song : songsAll) {
<<<<<<< HEAD
                        if (song.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
>>>>>>> Searchbar onquery listener text
=======
                        if (song.getTitle().toLowerCase().contains(filterPattern)) {
                            Log.d(TAG, "song found");
>>>>>>> Search view functionality completed
                            filteredList.add(song);
                        }
                    }
                }
<<<<<<< HEAD
<<<<<<< HEAD
                Log.d(TAG, "search results");
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                for (Song song : filteredList) {
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                }
=======
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
>>>>>>> Searchbar onquery listener text
=======
                Log.d(TAG, "search results");
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                for (Song song: filteredList){
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                }
>>>>>>> Search view functionality completed
                return filterResults;
            }

            // run on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
<<<<<<< HEAD
<<<<<<< HEAD
                //songs.clear();
                //songs.addAll((Collection<? extends Song>) filterResults.values);
                songs = (List<Song>) filterResults.values;
=======
                songs.clear();
                songs.addAll((Collection<? extends Song>) filterResults.values);
>>>>>>> Searchbar onquery listener text
=======
                //songs.clear();
                //songs.addAll((Collection<? extends Song>) filterResults.values);
                songs = (List<Song>) filterResults.values;
                for (Song song: songs){
                    Log.d(TAG, "filtered song: " + song.getTitle());
                    Log.d(TAG, "filtered song: " + song.getTitle());
                }
>>>>>>> Search view functionality completed
                notifyDataSetChanged();
            }
        };
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Internal ViewHolder model for each item.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
=======
//    // Clean all elements of the recycler
//    public void clear() {
//        songs.clear();
//        notifyDataSetChanged();
//    }
//
//    // Add a list of items -- change to type used
//    public void addAll(List<Song> list) {
//        songs.addAll(list);
//        notifyDataSetChanged();
//    }

=======
>>>>>>> Search view functionality completed
    // Internal ViewHolder model for each item.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
>>>>>>> Searchbar onquery listener text
        ImageView ivAlbum;
        TextView tvTitle;
        TextView tvArtist;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            itemView.setOnClickListener(this);
        }

        public void bind(Song song) {
            tvTitle.setText(song.getTitle());
            tvArtist.setText(song.getArtist());
            Glide.with(context).load(song.getURL()).into(ivAlbum);
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
                Intent intent = new Intent(context, SongQueueActivity.class);
                // serialize the post using parceler, use its short name as a key
<<<<<<< HEAD
                intent.putExtra(EXTRA_ALBUMICONURL, song.getURL());
                intent.putExtra(EXTRA_SONGTITLE, song.getTitle());
                intent.putExtra(EXTRA_SONGARTIST, song.getArtist());
                intent.putExtra(EXTRA_SONGOBJECTID, song.getObjectId());
                intent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
                // show the activity
                Log.d(TAG, "ssong selected");
=======
                intent.putExtra("albumiconurl", song.getURL());
                intent.putExtra("songtitle", song.getTitle());
                intent.putExtra("songartist", song.getArtist());
                intent.putExtra("songObjectid", song.getObjectId());
                intent.putExtra("playlistobjectid2", playlistObjectId);
                // show the activity
<<<<<<< HEAD
>>>>>>> Searchbar onquery listener text
=======
                Log.d(TAG, "song selected");
>>>>>>> Search view functionality completed
                context.startActivity(intent);
                Toast.makeText(context, "Song select", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
