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
import com.example.musicca.activities.EditProfileActivity;
import com.example.musicca.activities.LoginActivity;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    private static final String EXTRA_MUSICBIO = "musicbio";
    private static final String EXTRA_PROFILEIMAGEURL = "profileimageurl";

    private TextView tvUsername;
    private TextView tvMusicBio;
    private ImageView ivProfileImage;
    private Button btnEditProfile;
    private Button btnLogout;
    private String profileImageURL;

    private ParseUser parseUser = ParseUser.getCurrentUser();

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
        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);

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
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                if (ParseUser.getCurrentUser() == null) {
                    goLoginActivity();
                }
            }
        });
    }

    private void goEditProfile() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        i.putExtra(EXTRA_MUSICBIO, tvMusicBio.getText().toString());
        i.putExtra(EXTRA_PROFILEIMAGEURL, profileImageURL);
        startActivity(i);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }


}
