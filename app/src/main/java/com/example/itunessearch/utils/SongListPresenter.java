package com.example.itunessearch.utils;

import com.example.itunessearch.api.APIservice;
import com.example.itunessearch.api.ServiceFactory;
import com.example.itunessearch.api.TrackModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListPresenter implements SongListContract.Presenter {

    private SongListContract.View songListView;

    public SongListPresenter(SongListContract.View songListView) {
        this.songListView = songListView;
    }

    @Override
    public void getTracks(String term) {
        APIservice service = ServiceFactory.getInstance();
        Call<TrackModel> trackModelCall = service.getTracks(term);
        trackModelCall.enqueue(new Callback<TrackModel>() {
            @Override
            public void onResponse(Call<TrackModel> call, Response<TrackModel> response) {
                TrackModel trackModel = response.body();
                if (trackModel.getResultCount() > 0) {
                    songListView.displayTracks(trackModel.getTracks());
                } else {
                    songListView.displayMessage("No songs found, Try again.");
                }
            }

            @Override
            public void onFailure(Call<TrackModel> call, Throwable t) {
                songListView.displayMessage("No songs found, Try again.");
            }
        });
    }
}
