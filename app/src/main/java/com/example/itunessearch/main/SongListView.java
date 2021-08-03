package com.example.itunessearch.main;



import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itunessearch.R;
import com.example.itunessearch.api.Track;
import com.example.itunessearch.utils.SongAdapter;
import com.example.itunessearch.utils.SongListContract;
import com.example.itunessearch.utils.SongListPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SongListView extends AppCompatActivity implements SongListContract.View {

    Context context;
    LinearLayout main;
    TextView txtNoSongs;
    RecyclerView listTracks;

    private List<Track> dataTracks = new ArrayList<>();
    private SongAdapter adapter;

    SongListPresenter presenter;

    public SongListView() {
        presenter = new SongListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.song_list);

        context = SongListView.this;

        main = (LinearLayout) findViewById(R.id.song_list_main);
        txtNoSongs  = (TextView) findViewById(R.id.txtNoSongs);
        listTracks = (RecyclerView) findViewById(R.id.listSongs);

        adapter = new SongAdapter(context, dataTracks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listTracks.setLayoutManager(mLayoutManager);
        listTracks.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        listTracks.setItemAnimator(new DefaultItemAnimator());
        listTracks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for Songs, Artists & More");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void search(final String strTerm) {
        txtNoSongs.setVisibility(View.GONE);
        listTracks.setVisibility(View.VISIBLE);

        dataTracks.clear();
        adapter.notifyDataSetChanged();

        setLoadingIndicator(true);
        listTracks.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getTracks(strTerm);
            }
        }, 2000);
    }

    @Override
    public void displayTracks(List<Track> dataTracks) {
        setLoadingIndicator(false);
        this.dataTracks.clear();
        this.dataTracks.addAll(dataTracks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listTracks.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
    }
}
