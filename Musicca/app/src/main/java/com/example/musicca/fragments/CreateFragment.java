package com.example.musicca.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.musicca.R;
import com.example.musicca.activities.QueueActivity;
import com.example.musicca.models.Playlist;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_PLAYLISTCODE = "playlistcode";
    private static final String EXTRA_PLAYLISTNAME = "playlistname";

    public static final String TAG = "CreateFragment";
    public static final int WIDTH = 300;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private ImageView ivPlaylistIcon;
    private TextView tvSetPlaylistIcon;
    private Button btnTakePhoto;
    private Button btnChoosePhoto;
    private EditText etPlaylistname_create;
    private Button btnCreatePlaylist;
    private Button btnGenCode;
    private TextView tvGenCode;


    private String playlistObjectId;
    private File photoFile;
    private String photoFileName = "photo.jpg";

    private String generatedCode;

    public final static int PICK_PHOTO_CODE = 1046;
    public static final String KEY_PLAYLISTICON = "playlistIcon";
    public static final String KEY_PLAYLISTNAME = "name";
    public static final String KEY_PLAYLISTCODE = "inviteCode";


    private ParseUser parseUser = ParseUser.getCurrentUser();
    public Playlist playlistPublic = new Playlist();

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
        btnChoosePhoto = view.findViewById(R.id.btnChoosePhoto);
        btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        etPlaylistname_create = view.findViewById(R.id.etPlaylistname_create);
        btnCreatePlaylist = view.findViewById(R.id.btnCreatePlaylist);
        tvSetPlaylistIcon = view.findViewById(R.id.tvSetPlaylistIcon);
        btnGenCode = view.findViewById(R.id.btnGenCode);
        tvGenCode = view.findViewById(R.id.tvGenCode);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto();
            }
        });
        btnGenCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
                List<Playlist> allPlaylists = new ArrayList<>();

                try {
                    allPlaylists = query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error generating code1!", Toast.LENGTH_SHORT).show();
                }

                try {
                    generatedCode = generateUniqueCode(allPlaylists);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error generating code2!", Toast.LENGTH_SHORT).show();
                }

                tvGenCode.setText(generatedCode);
            }
        });
        btnCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // upon clicking we want to collect all information and create a post out of it
                // we will need description, user and image
                String playlistName = etPlaylistname_create.getText().toString();

                if (playlistName.isEmpty()) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ivPlaylistIcon.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePlaylist(playlistName, generatedCode, parseUser, photoFile);
            }
        });

    }

    private String generateUniqueCode(List<Playlist> allPlaylists) throws ParseException {
        String generatedCode = String.format("%04d", new Random().nextInt(10000));

        if (isCodeUnique(generatedCode, allPlaylists) == true) {
            return generatedCode;
        } else {
            return generateUniqueCode(allPlaylists);
        }
    }

    private boolean isCodeUnique(String code, List<Playlist> playlists) {

        if (playlists.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < playlists.size(); i++) {
                if (TextUtils.equals(code, playlists.get(i).getInvitecode())) {
                    return false;
                }
            }
            return true;
        }
    }


    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    private void savePlaylist(String playlistName, String playlistCode, ParseUser owner, File photoFile) {
        playlistPublic.setName(playlistName);
        playlistPublic.setInvitecode(playlistCode);
        playlistPublic.setOwner(owner);

        playlistPublic.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                }

                Log.i(TAG, "Playlist created successfully!");
                Toast.makeText(getContext(), "Playlist created successfully!", Toast.LENGTH_SHORT).show();
                // if we saved successfully, clear the text from the description and not save text more than once
                etPlaylistname_create.setText("");
                tvGenCode.setText("");
                ivPlaylistIcon.setImageResource(0);

                playlistObjectId = playlistPublic.getObjectId();
                gotoPlaylist();

            }
        });

    }

    private void gotoPlaylist() {
        Log.e("PLAYLIST 0", "playlistObjectId" + playlistObjectId);
        Intent newintent = new Intent(getContext(), QueueActivity.class);
        newintent.putExtra(EXTRA_PLAYLISTNAME, playlistPublic.getName());
        newintent.putExtra(EXTRA_PLAYLISTCODE, playlistPublic.getInvitecode());
        newintent.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(newintent);
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

            // Load the selected image into a preview
            ivPlaylistIcon.setImageBitmap(selectedImage);

            ParseFile parsefile = new ParseFile(image);
            playlistPublic.put(KEY_PLAYLISTICON, parsefile);
            playlistPublic.saveInBackground();
            Toast.makeText(getContext(), "Playlist icon updated successfully!", Toast.LENGTH_SHORT).show();

        }
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // Load the taken image into a preview
                ivPlaylistIcon.setImageBitmap(takenImage);

                ParseFile parsefile = new ParseFile(photoFile);
                playlistPublic.put(KEY_PLAYLISTICON, parsefile);
                playlistPublic.saveInBackground();
                Toast.makeText(getContext(), "Playlist icon updated successfully!", Toast.LENGTH_SHORT).show();
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}