package com.example.musicca.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        return KEY_NAME;
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getInvitecode() {
        return KEY_INVITECODE;
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
<<<<<<< HEAD
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
=======
    public ArrayList<Song> getSongList(){
        return (ArrayList<Song>) get(KEY_SONGS);
=======
    public List<String> getSongList() {
        List<String> songobjectIDs = getList(KEY_SONGS);
        if (songobjectIDs != null)
        {
            Log.d("listof songs","size" + songobjectIDs.size());
            return songobjectIDs;
        }
        else {
            return null;
        }


//        JSONArray jsonArray = getJSONArray(KEY_SONGS);
////        JSONArray jsonArray = (JSONArray) get(KEY_SONGS);
//        if (get(KEY_SONGS) != null) {
//            Log.d("json array", jsonArray.toString());
//            for (int i = 0; i < jsonArray.length(); i++) {
//                songobjectIDs.add(jsonArray.get(i).toString());
//            }
//        }

>>>>>>> Play song, login error handling, contant string extras for intents
    }
    public void setSongList(List<String> objectIDs) {
        JSONArray jsonArray = new JSONArray(objectIDs);
        put(KEY_SONGS, jsonArray);
=======

    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) get(KEY_SONGS);
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
    }

    public void setSongs(ArrayList<Song> songs) {
        put(KEY_SONGS, songs);
    }
    public void setSong(Song song) {
<<<<<<< HEAD
        put(KEY_SONGS, song);
>>>>>>> Populate newly created playlist
    }


    //    public ArrayList<Song> getSongs() {
//        return (ArrayList<Song>) get(KEY_SONGS);
//    }

<<<<<<< HEAD
    public void setSong(Song song) {
        put(KEY_SONGS, song);
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
>>>>>>> Populate newly created playlist
=======
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
    }
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"

}