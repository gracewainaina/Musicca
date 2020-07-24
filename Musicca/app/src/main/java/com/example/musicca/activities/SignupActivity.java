package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicca.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    private EditText etNewusername;
    private EditText etNewpassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        etNewpassword = findViewById(R.id.etNewpassword);
        etNewusername = findViewById(R.id.etNewusername);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up button");
                // Create the ParseUser
                ParseUser user = new ParseUser();
                Log.d(TAG, "user created 1");
                signupUser(user);
                Log.d(TAG, "user created 2");
            }
        });
    }

    private void signupUser(ParseUser parseUser) {
        // Set core properties
        parseUser.setUsername(etNewusername.getText().toString());
        parseUser.setPassword(etNewpassword.getText().toString());
        Log.d(TAG, "user properties stored");
        // Invoke signUpInBackground

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with sign up", e);
                    Toast.makeText(SignupActivity.this, "Issue with signup!", Toast.LENGTH_SHORT).show();
                }
                goMainActivity();
                Toast.makeText(SignupActivity.this, "Sign up Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        Log.d(TAG, "going to main activity");
        finish();
    }
}