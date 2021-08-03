package com.example.itunessearch.utils;

import com.example.itunessearch.api.Track;

import java.util.List;

public class SongListContract {

    public interface View {
        void displayMessage(String message);

        void setLoadingIndicator(boolean isLoading);

        void displayTracks(List<Track> dataTracks);
    }

    interface Presenter {
        void getTracks(String term);
    }
}
