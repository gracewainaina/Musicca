package com.example.musicca.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Like")
public class Like extends ParseObject {

    public static final String KEY_SONG = "likedsong";
    public static final String KEY_PLAYLIST = "likedplaylist";
    public static final String KEY_USER = "likeduser";

    public String getKeySong() {
        return getString(KEY_SONG);
    }

    public void setKeySong(String songObjectId) {
        put(KEY_SONG, songObjectId);
    }

    public String getKeyPlaylist() {
        return getString(KEY_PLAYLIST);
    }

    public void setKeyPlaylist(String playlistObjectId) {
        put(KEY_PLAYLIST, playlistObjectId);
    }

    public String getKeyUser() {
        return getString(KEY_USER);
    }

    public void setKeyUser(String userObjectId) {
        put(KEY_USER, userObjectId);
    }
}
