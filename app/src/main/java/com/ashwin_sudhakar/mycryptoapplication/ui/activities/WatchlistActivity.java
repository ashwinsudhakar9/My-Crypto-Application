package com.ashwin_sudhakar.mycryptoapplication.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin_sudhakar.mycryptoapplication.R;
import com.ashwin_sudhakar.mycryptoapplication.adapters.AdapterWatchlist;
import com.ashwin_sudhakar.mycryptoapplication.helpers.DBHandler;
import com.ashwin_sudhakar.mycryptoapplication.model.ModelCoins;

import java.util.ArrayList;

public class WatchlistActivity extends AppCompatActivity {

    private ArrayList<ModelCoins> coinsArrayList= new ArrayList<>();;

    private DBHandler dbHandler;

    private AdapterWatchlist adapterWatchlist;

    private RecyclerView recycler_Coins;

    private Toolbar toolbar;
    private TextView titleToolbar;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        backIcon = findViewById(R.id.back_icon);
        titleToolbar = findViewById(R.id.title_toolbar);
        recycler_Coins = findViewById(R.id.recycler_Coins);

        titleToolbar.setText("Watchlist");
        backIcon.setOnClickListener(view -> {
            onBackPressed();
        });

        dbHandler = new DBHandler(WatchlistActivity.this);


        coinsArrayList = dbHandler.readCoins();

        adapterWatchlist = new AdapterWatchlist(coinsArrayList, WatchlistActivity.this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WatchlistActivity.this, RecyclerView.VERTICAL, false);

        recycler_Coins.setLayoutManager(linearLayoutManager);


        recycler_Coins.setAdapter(adapterWatchlist);

    }
}
