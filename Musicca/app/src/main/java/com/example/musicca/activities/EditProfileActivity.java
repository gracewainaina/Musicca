package com.example.musicca.activities;

<<<<<<< HEAD
<<<<<<< HEAD
=======
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Set up Profile Photo and update music bio
>>>>>>> Set up Profile Photo and update music bio
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

>>>>>>> Edit Profile Activity
=======
>>>>>>> resolved conflict in modify-playlist branch
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

<<<<<<< HEAD
<<<<<<< HEAD
public class EditProfileActivity extends AppCompatActivity {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public final static int PICK_PHOTO_CODE = 1046;
    public static final String KEY_PROFILEIMAGE = "profileimage";
    public static final String KEY_MUSICBIO = "musicbio";

    private static final String TAG = "EditProfileActivity";
    private ParseUser parseUser = ParseUser.getCurrentUser();

    private ImageView ivProfileImage;
    private Button btnTakePhoto;
    private Button btnChoosePhoto;
=======
=======
>>>>>>> Set up Profile Photo and update music bio
=======
>>>>>>> resolved conflict in modify-playlist branch
public class EditProfileActivity extends AppCompatActivity {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public final static int PICK_PHOTO_CODE = 1046;
    public static final String KEY_PROFILEIMAGE = "profileimage";
    public static final String KEY_MUSICBIO = "musicbio";

    private static final String TAG = "EditProfileActivity";
    private ParseUser parseUser = ParseUser.getCurrentUser();

    private ImageView ivProfileImage;
    private Button btnTakePhoto;
    private Button btnChoosePhoto;
    private EditText tvEditMusicBio;
    private Button btnChangeMusicBio;
    private String profileImageURL;

    // Camera
    private File photoFile;
    private String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);
        tvEditMusicBio = findViewById(R.id.tvEditMusicBio);
        btnChangeMusicBio = findViewById(R.id.btnChangeMusicBio);

        ParseFile parseFile = parseUser.getParseFile("profileimage");
        profileImageURL = parseFile.getUrl();
        if (parseFile != null) {
            Glide.with(this).load(profileImageURL).circleCrop().into(ivProfileImage);
        }

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

        btnChangeMusicBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMusicBio();
            }
        });

    }

    private void updateMusicBio() {
        parseUser.put(KEY_MUSICBIO, tvEditMusicBio.getText().toString());
        parseUser.saveInBackground();
        tvEditMusicBio.setText("");
        Toast.makeText(EditProfileActivity.this, "Music Bio updated successfully!", Toast.LENGTH_SHORT).show();


    }

    private void onPickPhoto() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(EditProfileActivity.this.getPackageManager()) != null) {
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
                ImageDecoder.Source source = ImageDecoder.createSource(EditProfileActivity.this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(EditProfileActivity.this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        //photoFile = getPhotoFileUri(photoFileName);

        photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(EditProfileActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // camera
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivProfileImage.setImageBitmap(takenImage);
                savePhoto();

            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        // gallery
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();


            // Load the selected image into a preview
            ivProfileImage.setImageBitmap(selectedImage);

            ParseFile parsefile = new ParseFile(image);
            parseUser.put(KEY_PROFILEIMAGE, parsefile);
            parseUser.saveInBackground();
            Toast.makeText(EditProfileActivity.this, "Profile picture updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePhoto() {
        ParseFile parsefile = new ParseFile(photoFile);
        parseUser.put(KEY_PROFILEIMAGE, parsefile);
        parseUser.saveInBackground();
        Toast.makeText(EditProfileActivity.this, "Profile picture updated successfully!", Toast.LENGTH_SHORT).show();
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Edit Profile Activity
<<<<<<< HEAD
}
=======
=======
>>>>>>> Set up Profile Photo and update music bio
}
>>>>>>> Set up Profile Photo and update music bio
=======
}
>>>>>>> resolved conflict in modify-playlist branch
