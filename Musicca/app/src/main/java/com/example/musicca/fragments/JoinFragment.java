package com.example.musicca.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicca.R;
import com.example.musicca.activities.CurrentPlaylistActivity;
import com.example.musicca.activities.QueueActivity;
import com.example.musicca.models.Playlist;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class JoinFragment extends Fragment {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    public static final String KEY_PLAYLISTNAME = "name";
    public static final String KEY_PLAYLISTCODE = "inviteCode";

    private EditText etPlaylistname_join;
    private EditText etPlaylistcode_join;
    private Button btnJoin;

    public String playlistObjectId;

    public JoinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etPlaylistcode_join = view.findViewById(R.id.etPlaylistcode_join);
        etPlaylistname_join = view.findViewById(R.id.etPlaylistname_join);
        btnJoin = view.findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryPlaylists();
            }
        });
    }

    private void queryPlaylists() {
        // Define the class we would like to query
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // Define our query conditions
        query.whereEqualTo(KEY_PLAYLISTCODE, etPlaylistcode_join.getText().toString());
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<Playlist>() {
            public void done(List<Playlist> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    Toast.makeText(getContext(), "Playlist was found!", Toast.LENGTH_SHORT).show();
                    playlistObjectId = itemList.get(0).getObjectId();
                    Toast.makeText(getContext(), "Playlist was found!", Toast.LENGTH_SHORT).show();
                    gotoPlaylist();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void gotoPlaylist() {
        Intent newintent = new Intent(getContext(), CurrentPlaylistActivity.class);
        newintent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(newintent);

    }
}