package com.example.musicca.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Song")
public class Song extends ParseObject {

    public static final String KEY_SPOTIFY_ID = "spotifyID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_URL = "artUrl";

    public String getSpotifyId(){
        return KEY_SPOTIFY_ID;
    }
    public void setSpotifyId(String spotifyid){
        put(KEY_SPOTIFY_ID, spotifyid);
    }
    public String getTitle(){
        return KEY_SPOTIFY_ID;
    }
    public void setTitle(String title){
        put(KEY_TITLE, title);
    }
    public String getArtist(){
        return KEY_ARTIST;
    }
    public void setArtist(String artist){
        put(KEY_ARTIST, artist);
    }
    public String getAlbum(){
        return KEY_ALBUM;
    }
    public void setAlbum(String album){
        put(KEY_ALBUM, album);
    }
    public String getURL(){
        return KEY_URL;
    }
    public void setURL(String url){
        put(KEY_URL, url);
    }
}
