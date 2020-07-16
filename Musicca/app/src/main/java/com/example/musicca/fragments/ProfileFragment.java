package com.example.musicca.fragments;

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
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    private static final String EXTRA_MUSICBIO = "musicbio";
    private static final String EXTRA_PROFILEIMAGEURL = "profileimageurl";

    private TextView tvUsername;
    private TextView tvMusicBio;
    private ImageView ivProfileImage;
    private Button btnProfileimage;
    private Button btnMusicBio;
    private Button btnLogout;

    private ParseUser parseUser = ParseUser.getCurrentUser();

    public static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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
            }
        });
    }
<<<<<<< HEAD
<<<<<<< HEAD

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

=======

    private void goEditProfile() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        i.putExtra("musicbio", tvMusicBio.getText().toString());
        i.putExtra("profileimageurl", profileImageURL);
        startActivity(i);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

>>>>>>> Search view functionality completed

=======
>>>>>>> Log out
}