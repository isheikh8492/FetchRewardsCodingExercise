package com.fetchrewards.codingexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Item> itemList = new ArrayList<>();
    private List<Item> itemListWithNameOnly = new ArrayList<>();
    private List<Item> sortedItemList = new ArrayList<>();
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

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.filterByName) {
            if (this.itemListWithNameOnly.isEmpty()) {
                this.itemListWithNameOnly = itemList.stream().filter(i -> !i.getName().equals(""))
                        .collect(Collectors.toList());
            }
            itemAdapter = new ItemAdapter(this.itemListWithNameOnly, this);
            recyclerView.setAdapter(itemAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            return true;
        }
        if (item.getItemId() == R.id.sortItems) {
            if (this.sortedItemList.isEmpty()) {
                this.sortedItemList = itemList.stream().sorted(Comparator
                        .comparing(Item::getListId)).collect(Collectors.toList());
            }
            itemAdapter = new ItemAdapter(this.sortedItemList, this);
            recyclerView.setAdapter(itemAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            return true;
        }
        if (item.getItemId() == R.id.allItems) {
            itemAdapter = new ItemAdapter(this.itemList, this);
            recyclerView.setAdapter(itemAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            return true;
        }
        return super.onOptionsItemSelected(item);
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