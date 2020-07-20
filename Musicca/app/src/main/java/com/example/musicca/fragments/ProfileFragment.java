package com.example.musicca.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
<<<<<<< Updated upstream
=======
import com.example.musicca.activities.EditProfileActivity;
>>>>>>> Stashed changes
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvMusicBio;
    private ImageView ivProfileImage;
<<<<<<< Updated upstream
    private Button btnProfileimage;
    private Button btnMusicBio;
    private Button btnLogout;

    private ParseUser parseUser = ParseUser.getCurrentUser();

    public static final String TAG = "ProfileFragment";
=======
    private Button btnEditProfile;
    private String profileImageURL;

    private ParseUser parseUser = ParseUser.getCurrentUser();
>>>>>>> Stashed changes

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvMusicBio = view.findViewById(R.id.tvMusicBio);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
<<<<<<< Updated upstream
        btnProfileimage = view.findViewById(R.id.btnProfileimage);
        btnMusicBio = view.findViewById(R.id.btnMusicBio);
        btnLogout = view.findViewById(R.id.btnLogout);

        tvUsername.setText(parseUser.getUsername());
        tvMusicBio.setText(parseUser.getString("musicbio"));
        ParseFile parseFile = parseUser.getParseFile("profileimage");
        if (parseFile != null) {
            Glide.with(getContext()).load(parseFile.getUrl()).circleCrop().into(ivProfileImage);
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
=======
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        tvUsername.setText(parseUser.getUsername());
        tvMusicBio.setText(parseUser.getString("musicbio"));
        ParseFile parseFile = parseUser.getParseFile("profileimage");
        profileImageURL = parseFile.getUrl();
        if (parseFile != null) {
            Glide.with(getContext()).load(profileImageURL).circleCrop().into(ivProfileImage);
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditProfile();
>>>>>>> Stashed changes
            }
        });
    }

    private void goEditProfile() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        i.putExtra("musicbio", tvMusicBio.getText().toString());
        i.putExtra("profileimageurl", profileImageURL);
    }


}