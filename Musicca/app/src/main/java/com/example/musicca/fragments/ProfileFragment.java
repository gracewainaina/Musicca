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
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.musicca.activities.EditProfileActivity;
import com.example.musicca.activities.LoginActivity;
=======
<<<<<<< Updated upstream
=======
import com.example.musicca.activities.EditProfileActivity;
>>>>>>> Stashed changes
>>>>>>> Edit Profile Activity
=======
import com.example.musicca.activities.EditProfileActivity;
>>>>>>> Attempt 2: Edit Profile Activity
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    private static final String EXTRA_MUSICBIO = "musicbio";
    private static final String EXTRA_PROFILEIMAGEURL = "profileimageurl";

    private TextView tvUsername;
    private TextView tvMusicBio;
    private ImageView ivProfileImage;
<<<<<<< HEAD
<<<<<<< HEAD
    private Button btnEditProfile;
=======
<<<<<<< Updated upstream
    private Button btnProfileimage;
    private Button btnMusicBio;
>>>>>>> Edit Profile Activity
    private Button btnLogout;
    private String profileImageURL;

    private ParseUser parseUser = ParseUser.getCurrentUser();

<<<<<<< HEAD
=======
    public static final String TAG = "ProfileFragment";
=======
=======
>>>>>>> Attempt 2: Edit Profile Activity
    private Button btnEditProfile;
    private String profileImageURL;

    private ParseUser parseUser = ParseUser.getCurrentUser();

>>>>>>> Edit Profile Activity
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
<<<<<<< HEAD
<<<<<<< HEAD
        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
=======
<<<<<<< Updated upstream
        btnProfileimage = view.findViewById(R.id.btnProfileimage);
        btnMusicBio = view.findViewById(R.id.btnMusicBio);
        btnLogout = view.findViewById(R.id.btnLogout);
>>>>>>> Edit Profile Activity

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
<<<<<<< HEAD
                if (ParseUser.getCurrentUser() == null) {
                    goLoginActivity();
                }
=======
=======
=======
>>>>>>> Attempt 2: Edit Profile Activity
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
<<<<<<< HEAD
>>>>>>> Stashed changes
>>>>>>> Edit Profile Activity
=======
>>>>>>> Attempt 2: Edit Profile Activity
            }
        });
    }

    private void goEditProfile() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
<<<<<<< HEAD
        i.putExtra(EXTRA_MUSICBIO, tvMusicBio.getText().toString());
        i.putExtra(EXTRA_PROFILEIMAGEURL, profileImageURL);
        startActivity(i);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
=======
        i.putExtra("musicbio", tvMusicBio.getText().toString());
        i.putExtra("profileimageurl", profileImageURL);
>>>>>>> Edit Profile Activity
    }


}