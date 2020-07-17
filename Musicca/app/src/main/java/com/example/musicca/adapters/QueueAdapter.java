package com.example.musicca.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.item_song_queue, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistEntry entry = mDataset.get(position);
        holder.bindEntry(entry);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void onItemSwipedRemove(RecyclerView.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.remove();
    }

    public void onItemSwipedLike(RecyclerView.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.like();
    }

    public void notifyPlaylistUpdated() {
        mDataset.clear();
        List<PlaylistEntry> entries = mPlaylist.getEntries();
        mDataset.addAll(entries);
        notifyDataSetChanged();
    }

    @Override
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
            }

            final SaveCallback callback = e -> {
                isRemoving = false;

                if(e != null) {
                    notifyPlaylistUpdated();
                    ToastHelper.makeText(mContext, "Could not remove song.", true);
                }
            };
            Party.getCurrentParty().getPlaylist().removeEntry(mEntry, callback);
        }

        @OnClick(R.id.ibLike)
        public void onClickLike() {
            like();
        }

        public void like() {
            NotificationHelper.updateInteractionTime();
            if(isRemoving || isLiking) return;
            isLiking = true;

            final boolean isLiked = mEntry.isLikedByUser();
            final String errorMessage = isLiked ? "Could not unlike song." : "Could not like song.";
            final SaveCallback callback = e -> {
                isLiking = false;
                if(e == null) {
                    displayLiked(!isLiked);
                } else {
                    displayLiked(isLiked);
                    ToastHelper.makeText(mContext, errorMessage, true);
                }
            };

            displayLiked(!isLiked);
            if(isLiked) {
                Party.getCurrentParty().getPlaylist().unlikeEntry(mEntry, callback);
            } else {
                Party.getCurrentParty().getPlaylist().likeEntry(mEntry, callback);
            }
        }

        /**
         * Displays a PlaylistEntry's information in the view
         * @param entry the PlaylistEntry whose information should be displayed
         */
        private void bindEntry(PlaylistEntry entry) {
            Log.d("Queue", "Binding " + entry.getSong().getTitle());
            if(mEntry != null && !mEntry.equals(entry)) {
                isLiking = false;
                isRemoving = false;
            }
            mEntry = entry;
            Song song = mEntry.getSong();
            tvTitle.setText(song.getTitle());
            displayLiked(mEntry.isLikedByUser());
            try {
                tvScore.setText(String.format("%s", mEntry.getScore().intValue()));
            } catch (NullPointerException e) {
                Log.e("QueueAdapter", "Entry has no score");
                tvScore.setText("0");
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(song.getArtist());
            String screenName = entry.getAddedBy();
            if (screenName != null) {
                stringBuilder.append(String.format(" · Added by %s", screenName));
            }

            tvArtist.setText(stringBuilder.toString());

            Glide.with(mContext)
                    .load(song.getImageUrl())
                    .placeholder(R.drawable.ic_album_placeholder)
                    .transform(new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.searchbar_radius)))
                    .into(ivAlbum);
        }

        private void displayLiked(boolean isLiked) {
            ibLike.setSelected(isLiked);
            if(isLiked) {
                ibLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                ibLike.setColorFilter(ContextCompat.getColor(mContext, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
    }
}
