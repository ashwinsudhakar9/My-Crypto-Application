package com.ashwin_sudhakar.mycryptoapplication.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ashwin_sudhakar.mycryptoapplication.R;
import com.ashwin_sudhakar.mycryptoapplication.adapters.AdapterCoinsList;
import com.ashwin_sudhakar.mycryptoapplication.helpers.DBHandler;
import com.ashwin_sudhakar.mycryptoapplication.model.ModelCoinDetails;
import com.ashwin_sudhakar.mycryptoapplication.ui.customViews.MyDialogSheet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.sql.SQLDataException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CoinsListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView coinsRecycler;
    private TextView txt_search;
    private EditText edt_search;
    Boolean isClicked = false;

    private final List<ModelCoinDetails> coinDetailsList = new ArrayList<>();

    private AdapterCoinsList adapterCoinsList;
    private FloatingActionButton fab_watchlist;
    private DBHandler dbHandler;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(CoinsListActivity.this);
        initViews();
        initSpinner();


    }

    private void initSpinner() {

        String[] items = new String[]{"USD", "INR", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    private void initClicks() {

        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = true;
                txt_search.setVisibility(View.GONE);
                edt_search.setVisibility(View.VISIBLE);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filter(s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        fab_watchlist.setOnClickListener(view -> {
            startActivity(new Intent(CoinsListActivity.this, WatchlistActivity.class));
        });


    }

    private void filter(String text) {
        ArrayList<ModelCoinDetails> filteredList = new ArrayList<>();
        for (ModelCoinDetails item : coinDetailsList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterCoinsList.filterList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        coinDetailsList.clear();

        if (isNetworkConnected()) {
            fetchListResponse();
            initClicks();
        } else {
            showSettingsDialog();
        }

    }

    @Override
    public void onBackPressed() {
        if (isClicked) {
            fetchListResponse();
            edt_search.setVisibility(View.GONE);
            txt_search.setVisibility(View.VISIBLE);
            isClicked = false;
        } else {
            super.onBackPressed();
        }
    }

    private void fetchListResponse() {

        String BASE_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL, null,
                response -> {
                    Log.d("Response", response.toString());
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String symbol = response.getJSONObject(i).getString("symbol");
                            String name = response.getJSONObject(i).getString("name");
                            String imageUrl = response.getJSONObject(i).getString("image");
                            Long current_price = response.getJSONObject(i).getLong("current_price");
                            Long market_cap = response.getJSONObject(i).getLong("market_cap");
                            Integer market_cap_rank = response.getJSONObject(i).getInt("market_cap_rank");
                            Long total_volume = response.getJSONObject(i).getLong("total_volume");
                            Double high_24h = response.getJSONObject(i).getDouble("high_24h");
                            Double low_24h = response.getJSONObject(i).getDouble("low_24h");
                            Double price_change_24h = response.getJSONObject(i).getDouble("price_change_24h");
                            Double price_change_percentage_24h = response.getJSONObject(i).getDouble("price_change_percentage_24h");
                            Double market_cap_change_24h = response.getJSONObject(i).getDouble("market_cap_change_24h");
                            Double market_cap_change_percentage_24h = response.getJSONObject(i).getDouble("market_cap_change_percentage_24h");
                            String last_updated_Date = response.getJSONObject(i).getString("last_updated");
                            Double ath_change_percentage = response.getJSONObject(i).getDouble("ath_change_percentage");
                            Double atl_change_percentage = response.getJSONObject(i).getDouble("atl_change_percentage");


                            coinDetailsList.add(new ModelCoinDetails(dropdown.getSelectedItem().toString(), symbol, name, imageUrl, current_price, market_cap, market_cap_rank, total_volume, high_24h, low_24h,
                                    price_change_24h, price_change_percentage_24h, market_cap_change_24h, market_cap_change_percentage_24h, getUpdatedDate(last_updated_Date), ath_change_percentage, atl_change_percentage));
                        }
                        initRecyclerView();

                        adapterCoinsList.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("error", error.toString());
                    MyDialogSheet dialogSheet = new MyDialogSheet(CoinsListActivity.this);
                    dialogSheet.setTitle(getString(R.string.oops))
                            .setMessage(error.getMessage())
                            .setPositiveButton(getString(R.string.retry),
                                    v -> fetchListResponse())
                            .setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white))
                            .setButtonsColorRes(R.color.teal_700)
                            .setNegativeButton(getString(R.string.dialog_button_cancel),
                                    null);
                    dialogSheet.show();

                });
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(jsonObjectRequest);
    }

    public String getUpdatedDate(String notificationDate) {

        long notifDate = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC+04:00"));  //Timezone is IST!!!

            String splitDate[] = notificationDate.split("T");
            Date date = sdf.parse(splitDate[0] + " " + splitDate[1].substring(0, 8));
            if (!(date == null)) {
                notifDate = date.getTime();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar notifRecTime = Calendar.getInstance();
        notifRecTime.setTimeInMillis(notifDate);

        final String timeFormatString = "h:mm aa";
        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(notifDate);

        if (date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("MMM d'st', yy");
        else if (date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("MMM d'nd', yy");
        else if (date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("MMM d'rd', yy");
        else
            format = new SimpleDateFormat("MMM d'th', yy");

        String yourDate = format.format(notifDate);


        return yourDate
                + " "
                + DateFormat.format(timeFormatString, notifDate);

    }

    private void initViews() {

        coinsRecycler = findViewById(R.id.recycler_Coins);
        txt_search = findViewById(R.id.txt_search);
        edt_search = findViewById(R.id.edt_search);
        fab_watchlist = findViewById(R.id.fab_watchlist);
        dropdown = findViewById(R.id.spinner1);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CoinsListActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("This app needs internet connection to use this feature. You can grant them in from settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void initRecyclerView() {

        adapterCoinsList = new AdapterCoinsList(getApplicationContext(), coinDetailsList, (view, model, position) -> {


            bottomSheet(view, model, position);


        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        coinsRecycler.setLayoutManager(mLayoutManager);
        coinsRecycler.setItemAnimator(new DefaultItemAnimator());
        coinsRecycler.setAdapter(adapterCoinsList);
    }

    private void bottomSheet(View view, List<ModelCoinDetails> list, int position) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialog);

        View bottomsheetview = LayoutInflater.from(this).inflate(R.layout.activity_details, (LinearLayout) view.findViewById(R.id.detailsLayout));
//        bottomSheetDialog.setTitle("Report Details");
        TextView txt_heading = bottomsheetview.findViewById(R.id.txt_heading);
        TextView namePrice = bottomsheetview.findViewById(R.id.namePrice);
        TextView txt_Current_Price = bottomsheetview.findViewById(R.id.txt_Current_Price);
        TextView txt_marketCap = bottomsheetview.findViewById(R.id.txt_marketCap);
        TextView txt_TotalVolume = bottomsheetview.findViewById(R.id.txt_TotalVolume);
        TextView txt_marketCapRank = bottomsheetview.findViewById(R.id.txt_marketCapRank);
        TextView txt_Price_change_24h = bottomsheetview.findViewById(R.id.txt_Price_change_24h);
        TextView txt_Market_cap_change_24h = bottomsheetview.findViewById(R.id.txt_Market_cap_change_24h);
        TextView txt_Price24hHighLow = bottomsheetview.findViewById(R.id.txt_Price24hHighLow);
        TextView txt_AllTimeHigh = bottomsheetview.findViewById(R.id.txt_AllTimeHigh);
        TextView txt_AllTimeLow = bottomsheetview.findViewById(R.id.txt_AllTimeLow);
        ;
        if (list.get(position) != null) {

            final ModelCoinDetails dataItem = list.get(position);


            txt_heading.setText(dataItem.getName() + " Price and Market Stats");
            namePrice.setText(dataItem.getName() + " Price");
            if (dataItem.getCurrency() == "INR") {
                double inr = 74.31;
                double currentPrice = inr * Double.parseDouble(dataItem.getCurrent_price().toString());
                txt_Current_Price.setText("₹" + currentPrice);
            } else if (dataItem.getCurrency() == "EUR") {
                double eur = 0.89;
                double currentPrice = eur * Double.parseDouble(dataItem.getCurrent_price().toString());
                txt_Current_Price.setText("€" + currentPrice);
            } else {
                txt_Current_Price.setText("$" + dataItem.getCurrent_price().toString());
            }
            txt_marketCap.setText(dataItem.getMarket_cap().toString());
            txt_TotalVolume.setText(dataItem.getTotal_volume().toString());
            txt_marketCapRank.setText(dataItem.getMarket_cap_rank().toString());
            if (dataItem.getPrice_change_percentage_24h() < 0) {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                txt_Price_change_24h.setText("$" + String.format("%.2f", Double.parseDouble(dataItem.getPrice_change_percentage_24h().toString())) + "%");
            } else {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
                txt_Price_change_24h.setText("$" + String.format("%.2f", Double.parseDouble(dataItem.getPrice_change_percentage_24h().toString())) + "%");
            }
            if (dataItem.getMarket_cap_change_percentage_24h() < 0) {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                txt_Market_cap_change_24h.setText("$" + String.format("%.2f", Double.parseDouble(dataItem.getMarket_cap_change_percentage_24h().toString())) + "%");
            } else {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
                txt_Market_cap_change_24h.setText("$" + String.format("%.2f", Double.parseDouble(dataItem.getMarket_cap_change_percentage_24h().toString())) + "%");
            }
            txt_Price24hHighLow.setText("$" + String.format("%.2f", Double.parseDouble(dataItem.getHigh_24h().toString())) + " / " + "$" + String.format("%.2f", Double.parseDouble(dataItem.getLow_24h().toString())));
            if (dataItem.getAth_change_percentage() < 0) {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                txt_AllTimeHigh.setText(String.format("%.2f", Double.parseDouble(dataItem.getAth_change_percentage().toString())) + "%");
            } else {
                txt_AllTimeHigh.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
                txt_AllTimeHigh.setText(String.format("%.2f", Double.parseDouble(dataItem.getAth_change_percentage().toString())) + "%");
            }
            if (dataItem.getAtl_change_percentage() < 0) {
                txt_AllTimeLow.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                txt_AllTimeLow.setText(String.format("%.2f", Double.parseDouble(dataItem.getAtl_change_percentage().toString())) + "%");
            } else {
                txt_AllTimeLow.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
                txt_AllTimeLow.setText(String.format("%.2f", Double.parseDouble(dataItem.getAtl_change_percentage().toString())) + "%");
            }

                dbHandler.addCoinDetails(dataItem.getName(), dataItem.getSymbol(), dataItem.getImageUrl(), dataItem.getCurrent_price(), dataItem.getPrice_change_percentage_24h(), dataItem.getLast_updated_Date());

        }
        bottomSheetDialog.setContentView(bottomsheetview);
        bottomSheetDialog.show();


        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Instructions on bottomSheetDialog Dismiss
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                coinDetailsList.clear();
                fetchListResponse();
                break;
            case 1:
                coinDetailsList.clear();
                fetchListResponse();
                break;
            case 2:
                coinDetailsList.clear();
                fetchListResponse();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
