package com.example.musicca.models;

public class ComparableSong implements Comparable<ComparableSong> {

    public String songObjectId;
    public int numLikes;

    public ComparableSong(String songObjectId, int numLikes) {
        this.songObjectId = songObjectId;
        this.numLikes = numLikes;
    }

    @Override
    public int compareTo(ComparableSong comparableSong) {
        return comparableSong.numLikes - this.numLikes;
    }

    public String getSongObjectId() {
        return songObjectId;
    }

    public int getNumLikes() {
        return numLikes;
    }
}
