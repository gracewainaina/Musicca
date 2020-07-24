package com.example.musicca.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.musicca.R;
import com.example.musicca.activities.PartyActivity;
import com.example.musicca.adapters.QueueAdapter;
import com.example.musicca.models.Playlist;
import com.example.musicca.utilities.TouchItemCallbacks;
import com.parse.SaveCallback;

public class QueueFragment extends Fragment {
    private QueueAdapter queueAdapter;
    private Playlist playlist;
    private SaveCallback mPlaylistUpdatedCallback;

    RecyclerView rvQueue;
    LinearLayout textContainer;
    TextView tvError;

    public QueueFragment(Playlist mPlaylist) {
        playlist = mPlaylist;
    }

    /***
     * Creates a new instance of the QueueFragment
     * @return The new instance created
     */
    public static QueueFragment newInstance(Playlist mPlaylist) {
        QueueFragment fragment = new  QueueFragment(mPlaylist);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_queue, container, false);
    }

    /***
     * Set up the queueAdapter and recycler view when the fragment view is created.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvQueue = view.findViewById(R.id.rvQueue);
        textContainer = view.findViewById(R.id.textContainer);

        textContainer.setVisibility(playlist.getSongs().isEmpty() ? View.VISIBLE : View.INVISIBLE);

        queueAdapter = new QueueAdapter(getContext(), playlist.getSongs(), (PartyActivity) getActivity(), playlist);
        queueAdapter.setHasStableIds(true);

        TouchItemCallbacks callbacks = new TouchItemCallbacks(queueAdapter, getContext());
        new ItemTouchHelper(callbacks.likeCallback).attachToRecyclerView(rvQueue);

        rvQueue.setAdapter(queueAdapter);
        rvQueue.setLayoutManager(new LinearLayoutManager(getContext()));
        initializePlaylistUpdateCallback();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("queue fragment", "PAUSED");
        if(!mParty.isCurrentUserAdmin()) {
            mParty.disconnectFromLiveQuery();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("queue fragment", "RESUMED");
        mParty.reconnectToLiveQuery();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Deregister the callback when this fragment is destroyed
        mParty.getPlaylist().deregisterUpdateCallback(mPlaylistUpdatedCallback);
    }

    private void initializePlaylistUpdateCallback() {
        mPlaylistUpdatedCallback = e -> {
            if(e == null) {
                // TODO: make this thread safe
                List<PlaylistEntry> entries = mParty.getPlaylist().getEntries();
                getActivity().runOnUiThread(() -> {
                    queueAdapter.notifyPlaylistUpdated();
                    textContainer.setVisibility(entries.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                });
            }
        };
        mParty.getPlaylist().registerUpdateCallback(mPlaylistUpdatedCallback);
    }
}
}