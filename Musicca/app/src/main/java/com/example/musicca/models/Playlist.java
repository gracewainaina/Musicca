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
import org.json.JSONObject;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

=======
>>>>>>> Search view functionality completed
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Resolving git comments on camel case, logs, constant, unused code
    public List<String> getSongList() {
        List<String> songobjectIDs = getList(KEY_SONGS);
        if (songobjectIDs != null) {
            Log.d("listof songs", "size" + songobjectIDs.size());
            return songobjectIDs;
        } else {
            return null;
        }
<<<<<<< HEAD
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
=======
    public ArrayList<Song> getSongList(){
        return (ArrayList<Song>) get(KEY_SONGS);
    }
    public void setSongList(List<Song> songs) {
>>>>>>> Search view functionality completed
        put(KEY_SONGS, songs);
=======
    }

    public void setSongList(List<String> objectIDs) {
        JSONArray jsonArray = new JSONArray(objectIDs);
        put(KEY_SONGS, jsonArray);
>>>>>>> Resolving git comments on camel case, logs, constant, unused code
    }

    public void setSong(Song song) {
        put(KEY_SONGS, song);
    }
<<<<<<< HEAD
//    public ArrayList<Song> getSongs() {
//        return (ArrayList<Song>) get(KEY_SONGS);
//    }

<<<<<<< HEAD
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
=======

//    public void setSong(Song song) {
//        addUnique(KEY_SONGS, song);
//    }

//    public boolean contains(Song song){
//        List<Song> songs = getSongs();
//        for(Song eachsong : songs){
//            if (song.equals(song)){
//                return true;
//            }
//        }
//        return false;
//    }
>>>>>>> Search view functionality completed
=======
>>>>>>> Resolving git comments on camel case, logs, constant, unused code

}