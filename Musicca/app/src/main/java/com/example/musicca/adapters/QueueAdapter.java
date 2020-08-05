package com.example.musicca.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.activities.QueueActivity;
import com.example.musicca.activities.SongQueueActivity;
import com.example.musicca.models.Song;

import java.util.ArrayList;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> implements Filterable {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    public static final String DEFAULT_STRING_AFTER = "After/During";
    public static final String DEFAULT_STRING_BEFORE = "Before/During";

    private static final String TAG = "QueueAdapter";

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
                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(songsAll);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (Song song : songsAll) {
                        if (song.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(song);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            // run on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                songs = (List<Song>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Access the data result passed to the activity here
    // perform filtering through the filter dialog fragment
    public void performMultiFiltering(String yearAfter, String yearBefore, String songTitle, String songArtist) {

        List<Song> filteredList = new ArrayList<>();

        int searchYearAfter;
        int searchYearBefore;

        if (TextUtils.equals(yearAfter, DEFAULT_STRING_AFTER)) {
            searchYearAfter = 0;
        } else {
            searchYearAfter = Integer.valueOf(yearAfter);
        }

        if (TextUtils.equals(yearBefore, DEFAULT_STRING_BEFORE)) {
            searchYearBefore = 3000;
        } else {
            searchYearBefore = Integer.valueOf(yearBefore);
        }


        for (Song song : songsAll) {
            if ((yearAfter == DEFAULT_STRING_AFTER || Integer.valueOf(song.getYear().toLowerCase()) >= searchYearAfter) &&
                    (yearBefore == DEFAULT_STRING_BEFORE || Integer.valueOf(song.getYear().toLowerCase()) <= searchYearBefore) &&
                    (TextUtils.isEmpty(songTitle) || song.getTitle().toLowerCase().contains(songTitle)) &&
                    (TextUtils.isEmpty(songArtist) || song.getArtist().toLowerCase().contains(songArtist))) {

                filteredList.add(song);
            }
        }

        // modify the list of songs to contain the search results from the filter dialog fragment
        if (filteredList.size() == 0) {
            Toast.makeText(context, "Could not find the song!", Toast.LENGTH_SHORT).show();
        } else {
            songs = filteredList;
            notifyDataSetChanged();
        }
    }

    // Internal ViewHolder model for each item.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivAlbum;
        private TextView tvTitle;
        private TextView tvArtist;
        private TextView tvYear;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            tvYear = itemView.findViewById(R.id.tvYear);
            itemView.setOnClickListener(this);
        }

        public void bind(Song song) {
            tvTitle.setText(song.getTitle());
            tvArtist.setText(song.getArtist());
            tvYear.setText(song.getYear());
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
                intent.putExtra(EXTRA_ALBUMICONURL, song.getURL());
                intent.putExtra(EXTRA_SONGTITLE, song.getTitle());
                intent.putExtra(EXTRA_SONGARTIST, song.getArtist());
                intent.putExtra(EXTRA_SONGOBJECTID, song.getObjectId());
                intent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
                // show the activity
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, (View) ivAlbum, "album");
                context.startActivity(intent, options.toBundle());
                Toast.makeText(context, "Song selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}