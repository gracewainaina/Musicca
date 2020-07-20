package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class EditProfileActivity extends AppCompatActivity {

    private ParseUser parseUser = ParseUser.getCurrentUser();

    private ImageView ivProfileImage;
    private Button btnChangeImage;
    private EditText tvEditMusicBio;
    private Button btnChangeMusicBio;
    private String profileImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnChangeImage = findViewById(R.id.btnChangeImage);
        tvEditMusicBio = findViewById(R.id.tvEditMusicBio);
        btnChangeMusicBio = findViewById(R.id.btnChangeMusicBio);

        ParseFile parseFile = parseUser.getParseFile("profileimage");
        profileImageURL = parseFile.getUrl();
        if (parseFile != null) {
            Glide.with(this).load(profileImageURL).circleCrop().into(ivProfileImage);
        }

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnChangeMusicBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }
}