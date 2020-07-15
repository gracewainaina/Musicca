package com.example.musicca.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.musicca.R;
import com.parse.ParseFile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CreateFragment extends Fragment {

    public static final String TAG = "CreateFragment";
    public static final int WIDTH = 300;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private ImageView ivPlaylistIcon;
    private Button btnSetPlaylistIcon;
    private EditText etPlaylistname_create;
    private EditText etPlaylistcode_create;
    private Button btnCreatePlaylist;

    private File photoFile;
    private String photoFileName = "photo.jpg";
    public final static int PICK_PHOTO_CODE = 1046;
    public static final String KEY_PROFILEIMAGE = "profilephoto";
    private ParseUser parseUser = ParseUser.getCurrentUser();

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPlaylistIcon = view.findViewById(R.id.ivPlaylistIcon);
        btnSetPlaylistIcon = view.findViewById(R.id.btnSetPlaylistIcon);
        etPlaylistname_create = view.findViewById(R.id.etPlaylistname_create);
        etPlaylistcode_create = view.findViewById(R.id.etPlaylistcode_create);
        btnCreatePlaylist = view.findViewById(R.id.btnCreatePlaylist);

        btnSetPlaylistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto();

            }
        });


    }

    private void onPickPhoto() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            ParseFile parsefile = new ParseFile(image);

            parseUser.put(KEY_PROFILEIMAGE, parsefile);
            parseUser.saveInBackground();
            // Load the selected image into a preview
            ivPlaylistIcon.setImageBitmap(selectedImage);
        }
    }
}