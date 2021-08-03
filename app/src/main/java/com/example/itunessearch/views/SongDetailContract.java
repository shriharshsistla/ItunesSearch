package com.example.itunessearch.views;

import com.example.itunessearch.api.Track;

public class SongDetailContract {

    interface View {
        void displayMessage(String message);

        void displayTrack(Track track);
    }
}
