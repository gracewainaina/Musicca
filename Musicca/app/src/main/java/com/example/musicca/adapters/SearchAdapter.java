package com.example.musicca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SECTION_QUEUE = 0;
    private static final int TYPE_SECTION_ADD = 1;
    public static final int TYPE_SONG_IN_QUEUE = 2;
    private static final int TYPE_SONG_IN_ADD = 3;

    private static final String IN_QUEUE_TEXT = "Songs in playlist:";
    private static final String ADD_TEXT = "Add to playlist:";


    private Context mContext;
    private List<Song> songsInQueue;
    private List<Song> songsInAdd;
    private boolean mIsSectionQueueEnabled = false;
    private boolean mIsSectionAddEnabled = false;
    private Playlist playlist;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /***
     * Creates the adapter for holding songs
     * @param context The context the adapter is being created from
     */
    public SearchAdapter(Context context) {
        mContext = context;
        songsInAdd = new ArrayList<>();
        songsInQueue = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if(viewType == TYPE_SONG_IN_ADD || viewType == TYPE_SONG_IN_QUEUE) {
            return new SongViewHolder(inflater.inflate(R.layout.item_search_song, parent, false));
        } else {
            return new SectionViewHolder(inflater.inflate(R.layout.item_queue_song, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && songsInQueue.size() != 0) return TYPE_SECTION_QUEUE;
        else if(position == songsInQueue.size() + (songsInQueue.size() == 0 ? 0 : 1)) return TYPE_SECTION_ADD;
        else if(position < songsInQueue.size() + 1) return TYPE_SONG_IN_QUEUE;
        else return TYPE_SONG_IN_ADD;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SongViewHolder) {
            SongViewHolder songViewHolder = (SongViewHolder) holder;
            songViewHolder.showSongData(getSong(position, songViewHolder));
            songViewHolder.showLoading(false);
            if(songViewHolder.getItemViewType() == TYPE_SONG_IN_QUEUE) {
                songViewHolder.ibLike.setVisibility(View.GONE);
            } else {
                songViewHolder.ibLike.setVisibility(View.VISIBLE);
            }
        } else if(holder instanceof SectionViewHolder) {
            SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
            if(sectionViewHolder.getItemViewType() == TYPE_SECTION_QUEUE) {
                sectionViewHolder.tvSection.setText(IN_QUEUE_TEXT);
            } else {
                sectionViewHolder.tvSection.setText(ADD_TEXT);
            }
        }
    }

    // get the song for a position and viewholder
    private Song getSong(int position, SongViewHolder songViewHolder) {
        if(songViewHolder.getItemViewType() == TYPE_SONG_IN_QUEUE) {
            return songsInQueue.get(getQueuePosition(position));
        } else {
            return songsInQueue.get(getAddPosition(position));
        }
    }

    // number of sections being displayed
    private int getSectionCount() {
        return (mIsSectionAddEnabled ? 1 : 0) + (mIsSectionQueueEnabled ? 1 : 0);
    }

    // number of items in the adapter
    @Override
    public int getItemCount() {
        return songsInQueue.size() + songsInAdd.size() + getSectionCount();
    }

    // Gets the position in the songsInQueue list from the adapter position
    private int getQueuePosition(int position) {
        return position - (mIsSectionQueueEnabled ? 1 : 0);
    }

    // Gets the position in the songsInAdd list from the adapter position
    private int getAddPosition(int position) {
        return position - songsInQueue.size() - getSectionCount();
    }

    // Adds all songs from list into the adapter one at a time
    public void addAll(List<Song> list) {
        if(songsInAdd == null || songsInQueue == null || list == null) return;
        for(Song s : list) {
            if(playlist.contains(s)) {
                songsInQueue.add(s);
            } else {
                songsInAdd.add(s);
            }
        }
        notifyDataSetChanged();
        updateSections();
    }

    // Updates the section titles
    private void updateSections() {
        if(songsInQueue.size() == 0) {
            if(mIsSectionQueueEnabled) {
                mIsSectionQueueEnabled = false;
                notifyItemRemoved(0);
            }
        } else {
            if(!mIsSectionQueueEnabled) {
                mIsSectionQueueEnabled = true;
                notifyItemInserted(0);
            }
        }

        if(songsInAdd.size() == 0) {
            if(mIsSectionAddEnabled) {
                mIsSectionAddEnabled = false;
                notifyItemRemoved(getItemCount());
            }
        } else {
            if(!mIsSectionAddEnabled) {
                mIsSectionAddEnabled = true;
                notifyItemInserted(songsInQueue.size());
            }
        }
    }

    public void clear() {
        songsInAdd.clear();
        songsInQueue.clear();
        mIsSectionAddEnabled = false;
        mIsSectionQueueEnabled = false;
        notifyDataSetChanged();
    }

    public void onItemSwipedAdd(RecyclerView.ViewHolder viewHolder) {
        SongViewHolder vh = (SongViewHolder) viewHolder;
        notifyItemChanged(vh.getAdapterPosition());
        vh.onAdd();
    }

    // Internal ViewHolder model for each song.
    public class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbum;
        TextView tvTitle;
        TextView tvArtist;
        ImageButton ibLike;
        ProgressBar pbLoading;

        private Song mSong;

        // ensure the item can't be added multiple times
        private boolean mIsAdding = false;

        private SongViewHolder(View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            ibLike = itemView.findViewById(R.id.ibLike);
            pbLoading = itemView.findViewById(R.id.pbLoading);

            ButterKnife.bind(this, itemView);
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdd();
                }
            });
//            ibLike.OnClickListener(new View.OnClickListener)
//            @OnClick({R.id.clContainer, R.id.ibLike})
//            public void onClickAdd() {
//                onAdd();
//            }
        }
        private void onAdd() {
            if(getItemViewType() == TYPE_SONG_IN_QUEUE || mIsAdding) return;
            mIsAdding = true;
            showLoading(true);

            // Move song to "in queue" section
            int adapterPosition = getAdapterPosition();
            int inAddPosition = songsInAdd.indexOf(mSong);
            songsInAdd.remove(inAddPosition);
            int inQueuePosition = songsInQueue.size();
            songsInQueue.add(mSong);
            notifyItemMoved(adapterPosition, inQueuePosition + 1);
            updateSections();

            // Add song to server
            playlist.setSong(mSong);
            notifyDataSetChanged();
            updateSections();
        }

        /**
         * Displays a Song's information in the view
         * Song is cached in viewholder until updated
         * @param song the song whose information should be displayed
         */
        private void showSongData(Song song) {
            mSong = song;
            tvArtist.setText(song.getArtist());
            tvTitle.setText(song.getTitle());
            showLoading(mIsAdding);
            Glide.with(mContext)
                    .load(song.getURL())
                    .placeholder(R.drawable.albumicon)
                    .transform(new RoundedCorners(4))
                    .into(ivAlbum);
        }

        /**
         * Shows/hides the loading animation for when a song is being added
         * @param isLoading if true, show the loading animation; if false, hide it
         */
        public void showLoading(boolean isLoading) {
            pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            ibLike.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        }
    }

    /***
     * Internal ViewHolder model for each section heading
     */
    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvSection;

        private SectionViewHolder(View itemView) {
            super(itemView);
            tvSection = itemView.findViewById(R.id.tvSection);
            ButterKnife.bind(this, itemView);
        }
    }
}