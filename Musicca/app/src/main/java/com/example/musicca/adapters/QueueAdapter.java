package com.example.musicca.adapters;

import android.content.Context;
<<<<<<< HEAD
<<<<<<< HEAD
import android.content.Intent;
=======
<<<<<<< Updated upstream
=======
import android.content.Intent;
>>>>>>> Stashed changes
>>>>>>> Edit Profile Activity
=======
import android.content.Intent;
>>>>>>> Attempt 2: Edit Profile Activity
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.activities.SongQueueActivity;
import com.example.musicca.models.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> implements Filterable {
<<<<<<< HEAD

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
    private static final String TAG = "QueueAdapter";
=======
<<<<<<< Updated upstream
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
>>>>>>> Edit Profile Activity

    private Context context;
    private List<Song> songs;
    private List<Song> songsAll;
    private String playlistObjectId;

    // Creates the adapter for holding playlist
<<<<<<< HEAD
=======
    public QueueAdapter(Context context, List<Song> songs, PartyActivity partyActivity, Playlist playlist) {
        mContext = context;
        mPartyActivity = partyActivity;
        mSongs = new ArrayList<>(songs);
        mPlaylist = playlist;
=======
=======
>>>>>>> Attempt 2: Edit Profile Activity
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> implements Filterable {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String TAG = "QueueAdapter";
    private Context context;
    private List<Song> songs;
    private List<Song> songsAll;
    private String playlistObjectId;

    // Creates the adapter for holding playlist
>>>>>>> Edit Profile Activity
    public QueueAdapter(Context context, List<Song> songs, String playlistObjectId) {
        this.context = context;
        this.songs = songs;
        this.songsAll = new ArrayList<>(songs);
        this.playlistObjectId = playlistObjectId;
        Log.d(TAG, "length of this.songsAll " + this.songsAll.size());
        Log.d(TAG, "length of this.songs " + this.songs.size());
        Log.d(TAG, "length of songs " + songs.size());
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        Log.d("PLAYLIST OBJECT ID", "playlistObjectId" + playlistObjectId);
=======
>>>>>>> Stashed changes
>>>>>>> Edit Profile Activity
=======
>>>>>>> Attempt 2: Edit Profile Activity
=======
        Log.d("PLAYLIST OBJECT ID", "playlistObjectId" + playlistObjectId);
>>>>>>> Populate newly created playlist
=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
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
<<<<<<< HEAD
<<<<<<< HEAD
    public Filter getFilter() {
        return new Filter() {
            // run background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Song> filteredList = new ArrayList<>();
                // if string is empty, return the entire list or 'charSequence.toString().isEmpty()'
                if (charSequence == null || charSequence.length() == 0) {
                    Log.d(TAG, "nothing typed yet");
                    filteredList.addAll(songsAll);
                } else {
=======
<<<<<<< Updated upstream
    public long getItemId(int position) {
        return mDataset.get(position).getObjectId().hashCode();
    }

    /***
     * Internal ViewHolder model for each item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAlbum) ImageView ivAlbum;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvArtist) TextView tvArtist;
        @BindView(R.id.ibLike) ImageButton ibLike;
        @BindView(R.id.clItem) ConstraintLayout clItem;
        @BindView(R.id.tvScore) TextView tvScore;

        boolean isRemoving = false;
        boolean isLiking = false;

        private PlaylistEntry mEntry;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                if (mEntry == null) { return; }
                if (Party.getCurrentParty().isCurrentUserAdmin()) {
                    mMainActivity.getBottomPlayerFragment().onPlayNew(mEntry.getSong().getSpotifyId());
                } else {
                    like();
                }
            });
        }

        private void remove() {
            if(isRemoving || isLiking) return;
            isRemoving = true;

            final int index = mDataset.indexOf(mEntry);
            if (index >= 0) {
                mDataset.remove(index);
                notifyItemRemoved(index);
=======
=======
>>>>>>> Attempt 2: Edit Profile Activity
    public Filter getFilter() {
        return new Filter() {
            // run background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Song> filteredList = new ArrayList<>();
                // if string is empty, return the entire list or 'charSequence.toString().isEmpty()'
                if (charSequence == null || charSequence.length() == 0) {
                    Log.d(TAG, "nothing typed yet");
                    filteredList.addAll(songsAll);
                }
                else {
>>>>>>> Edit Profile Activity
                    Log.d(TAG, "check if song found");
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    Log.d(TAG, "length of songsAll " + songsAll.size() + filterPattern);
                    for (Song song : songsAll) {
                        if (song.getTitle().toLowerCase().contains(filterPattern)) {
                            Log.d(TAG, "song found");
                            filteredList.add(song);
                        }
                    }
<<<<<<< HEAD
=======
                }
                Log.d(TAG, "search results");
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                for (Song song: filteredList){
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                }
                return filterResults;
            }

            // run on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //songs.clear();
                //songs.addAll((Collection<? extends Song>) filterResults.values);
                songs = (List<Song>) filterResults.values;
                for (Song song: songs){
                    Log.d(TAG, "filtered song: " + song.getTitle());
                    Log.d(TAG, "filtered song: " + song.getTitle());
                }
                notifyDataSetChanged();
            }
        };
    }

    // Internal ViewHolder model for each item.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivAlbum;
        TextView tvTitle;
        TextView tvArtist;
<<<<<<< HEAD
>>>>>>> Stashed changes

                if(e != null) {
                    notifyPlaylistUpdated();
                    ToastHelper.makeText(mContext, "Could not remove song.", true);
>>>>>>> Edit Profile Activity
                }
                Log.d(TAG, "search results");
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                for (Song song : filteredList) {
                    Log.d(TAG, "filtered song in filteredList: " + song.getTitle());
                }
                return filterResults;
            }

            // run on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //songs.clear();
                //songs.addAll((Collection<? extends Song>) filterResults.values);
                songs = (List<Song>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Internal ViewHolder model for each item.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

=======

        public ViewHolder(View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            itemView.setOnClickListener(this);
        }

>>>>>>> Attempt 2: Edit Profile Activity
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
                intent.putExtra("playlistobjectid", playlistObjectId);
                // show the activity
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> Attempt 2: Edit Profile Activity
=======
                Log.d(TAG, "ssong selected");
>>>>>>> Populate newly created playlist
=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
                context.startActivity(intent);
                Toast.makeText(context, "Song select", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
