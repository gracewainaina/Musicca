package com.example.musicca.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.musicca.R;

public class JoinFragment extends Fragment {

    private EditText etPlaylistname_join;
    private EditText etPlaylistcode_join;
    private Button btnJoin;

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

    }
}