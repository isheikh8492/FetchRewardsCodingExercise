package com.fetchrewards.codingexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Item> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    private TextView errorTxtView;


    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        errorTxtView = findViewById(R.id.error);
        errorTxtView.setVisibility(View.GONE);

        // Updated to include border width and corner radius
        BorderItemDecoration borderItemDecoration = new BorderItemDecoration(this);
        recyclerView.addItemDecoration(borderItemDecoration);

        doDownload();
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void doDownload() {
        ItemDownloader.downloadItems(this);
    }

    public void updateData(List<Item> itemList) {
        if ((itemList == null) || (!(hasNetworkConnection()))) {
            setErrorText();
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        errorTxtView.setVisibility(View.GONE);
        this.itemList = itemList;
        itemAdapter = new ItemAdapter(this.itemList, this);
        recyclerView.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setErrorText() {
        recyclerView.setVisibility(View.GONE);
        errorTxtView.setVisibility(View.VISIBLE);
    }
}