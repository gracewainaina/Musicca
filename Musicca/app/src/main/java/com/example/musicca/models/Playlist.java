package com.example.musicca.models;

<<<<<<< HEAD
<<<<<<< HEAD
import android.util.Log;

=======
<<<<<<< Updated upstream
=======
import android.util.Log;

import androidx.annotation.Nullable;

import com.parse.FunctionCallback;
>>>>>>> Stashed changes
>>>>>>> Searchbar onquery listener text
=======
import android.util.Log;

>>>>>>> Attempt 2 [lost files] Searchbar onquery listener text
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.Date;
import java.util.List;
=======
<<<<<<< Updated upstream
=======
import java.util.Arrays;
=======
>>>>>>> Attempt 2 [lost files] Searchbar onquery listener text
import java.util.Date;
import java.util.List;
<<<<<<< HEAD
>>>>>>> Stashed changes
>>>>>>> Searchbar onquery listener text
=======
>>>>>>> Attempt 2 [lost files] Searchbar onquery listener text

@ParseClassName("Playlist")
public class Playlist extends ParseObject {

    public static final String KEY_OWNER = "Owner";
    public static final String KEY_NAME = "name";
    public static final String KEY_INVITECODE = "inviteCode";
    public static final String KEY_PLAYLISTICON = "playlistIcon";
    public static final String KEY_SONGS = "songs";

    public ParseUser getOwner() {
        return getParseUser(KEY_OWNER);
    }

    public void setOwner(ParseUser parseUser) {
        put(KEY_OWNER, parseUser);
    }

    public String getName() {
        return (String) get(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getInvitecode() {
        return (String) get(KEY_INVITECODE);
    }

    public void setInvitecode(String name) {
        put(KEY_INVITECODE, name);
    }

    public ParseFile getPlaylisticon() {
        return getParseFile(KEY_PLAYLISTICON);
    }

    public void setPlaylisticon(ParseFile parseFile) {
        put(KEY_PLAYLISTICON, parseFile);
    }

    public List<String> getSongList() {
        List<String> songobjectIDs = getList(KEY_SONGS);
        if (songobjectIDs != null) {
            Log.d("listof songs", "size" + songobjectIDs.size());
            return songobjectIDs;
        } else {
            return null;
        }
    }

    public void setSongList(List<String> objectIDs) {
        JSONArray jsonArray = new JSONArray(objectIDs);
        put(KEY_SONGS, jsonArray);
    }

<<<<<<< HEAD
    public void setSong(Song song) {
        put(KEY_SONGS, song);
=======
    public void setSongs(ArrayList<Song> songs) {
        put(KEY_SONGS, songs);
    }
    public void setSong(Song song) {
        add(KEY_SONGS, song);
    }

    public boolean contains(Song song){
        ArrayList<Song> songs = getSongs();
        for(Song eachsong : songs){
            if (song.equals(song)){
                return true;
            }
        }
        return false;
>>>>>>> Attempt 2 [lost files] Searchbar onquery listener text
    }

}