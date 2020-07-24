package com.example.musicca.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.parse.ParseException;
import com.parse.SaveCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;

public class BottomPlayerFragment extends Fragment {
    private static final String TAG = "BottomPlayerFragment";
    TextView tvTitle;
    TextView tvArtist;
    ImageView ivAlbum;
    ConstraintLayout mPlayerBackground;
    ImageButton ibExpandCollapse;
    ImageButton ibShare;

    private Playlist playlist;

    private SaveCallback mCurrentSongUpdatedCallback;

    private ConstraintSet mCollapsed;
    private ConstraintSet mExpanded;

    String mTrackName = "--";
    String mArtistName = "--";
    boolean isExpanded;
    private Typeface mBoldFont;
    private Typeface mNormalFont;

    public BottomPlayerFragment(Playlist mPlaylist) {
        playlist = mPlaylist;
    }

    public static BottomPlayerFragment newInstance(Playlist mPlaylist) {
        BottomPlayerFragment fragment = new BottomPlayerFragment(mPlaylist);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_player, container, false);
        ButterKnife.bind(this, view);
        mParty = Party.getCurrentParty();

        initializeSongUpdateCallback();
        setExpanded();
        return view;
    }

    // set bottom player
    public void setExpanded() {
        ViewGroup.LayoutParams params = mPlayerBackground.getLayoutParams();
        mPlayerBackground.setLayoutParams(params);
    }


    public void setVisibility(int visibility) {
        ivAlbum.setVisibility(visibility);
        ibShare.setVisibility(visibility);
    }


    void updateText() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mTrackName);
        tvTitle.setText(builder);
        tvArtist.setText(mArtistName);
        tvArtist.setVisibility(View.VISIBLE);

    }

    // Song update callback methods are only called in client fragment
    private void initializeSongUpdateCallback() {
        Log.d(TAG, "Initializing song update callback");
        mCurrentSongUpdatedCallback = e -> {
            if (e == null) {
                Song currentSong = mParty.getCurrentSong();
                if (currentSong != null) {
                    try {
                        currentSong.fetchIfNeeded(); // TODO - work around this fetch; add ParseCloud function??
                        mTrackName = currentSong.getTitle();
                        mArtistName = currentSong.getArtist();
                        getActivity().runOnUiThread(() -> {
                            updateText();
                            Glide.with(this)
                                    .load(currentSong.getImageUrl())
                                    .into(ivAlbum);
                        });
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    mTrackName = "--";
                    mArtistName = "--";
                    getActivity().runOnUiThread(() -> {
                        updateText();
                        ivAlbum.setImageDrawable(getResources().getDrawable(R.drawable.albumicon, null));
                    });
                }
            } else {
                Log.d(TAG, "Error in song update callback", e);
            }
        };
        mParty.registerCurrentlyPlayingUpdateCallback(mCurrentSongUpdatedCallback);
        mCurrentSongUpdatedCallback.done(null);
    }
}
