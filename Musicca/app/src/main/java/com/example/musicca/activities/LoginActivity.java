package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.musicca.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSU;
    private ImageView ivLogo;
    private TextView tvPromptSU;
    private TextView tvSpotify;
    private ImageView ivSpotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check if user is already logged in after opening the app again
        // if so, go to main activity
        if (ParseUser.getCurrentUser() != null) {
            goLoginspotifyActivity();
        }

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnSU = findViewById(R.id.btnSU);
        ivLogo = findViewById(R.id.ivLogo);
        tvPromptSU = findViewById(R.id.tvPromptSU);
        tvSpotify = findViewById(R.id.tvSpotify);
        ivSpotify = findViewById(R.id.ivSpotify);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();
                YoYo.with(Techniques.SlideOutRight).duration(700).playOn(findViewById(R.id.btnLogin));
                loginUser(username, password);

            }
        });

        btnSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // Check if there is an issue with login
                if (e != null) {
                    Log.e(TAG, "issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Upon logging in properly, navigate to the main activity using intent
                goLoginspotifyActivity();
                Toast.makeText(LoginActivity.this, "Musicca login success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goLoginspotifyActivity() {
        Intent i = new Intent(this, LoginSpotifyActivity.class);
        startActivity(i);
        finish();
    }

}