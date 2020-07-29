package com.example.musicca.models;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SongOnTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public SongOnTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            SongOnTouchListener.this.onDoubleTap(e);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            SongOnTouchListener.this.onSingleTapConfirmed(e);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            SongOnTouchListener.this.onLongPress(e);
            super.onLongPress(e);
        }
    }

    public void onSingleTapConfirmed(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onDoubleTap(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onLongPress(MotionEvent e) {
        // To be overridden when implementing listener
    }
}
