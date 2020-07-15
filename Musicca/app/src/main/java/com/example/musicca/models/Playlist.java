package com.example.musicca.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

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

    public  ParseFile getPlaylisticon() {
        return getParseFile(KEY_PLAYLISTICON);
    }
    public void setPlaylisticon(ParseFile parseFile) {
        put(KEY_PLAYLISTICON, parseFile);
    }


    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) get(KEY_SONGS);
    }

    public void setSongs(ArrayList<Song> comments) {
        put(KEY_SONGS, comments);
    }
}
