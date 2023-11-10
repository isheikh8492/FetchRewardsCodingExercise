package com.fetchrewards.codingexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Item> itemList = new ArrayList<>();
    private List<Item> itemListWithNameOnly = new ArrayList<>();
    private List<Item> sortedItemList = new ArrayList<>();
    private HashMap<Integer, List<Integer>> listIdMap = new HashMap<>();
    private List<Item> currentItemList = new ArrayList<>();
    private List<Integer> listIds = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    private LinearLayout errorLayout;

    private LinearLayoutManager linearLayoutManager;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private ArrayAdapter<Integer> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.drawer_list);

        drawerList.setOnItemClickListener(
                (parent, view, position, id) -> {
                    selectItem(position);
                    drawerLayout.closeDrawer(drawerList);
                }
        );

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        recyclerView = findViewById(R.id.recycler);
        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        errorLayout = findViewById(R.id.error);
        errorLayout.setVisibility(View.GONE);

        BorderItemDecoration borderItemDecoration = new BorderItemDecoration(this);
        recyclerView.addItemDecoration(borderItemDecoration);

        doDownload();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void selectItem(int position) {
        currentItemList.clear();
        Log.d(TAG, "selectItem: " + position);
        Integer listId = listIds.get(position);

        List<Integer> currentItemIds = listIdMap.get(listId);
        for (Integer id : currentItemIds) {
            Optional<Item> item = itemList.stream().filter(i -> i.getId().equals(id)).findFirst();
            item.ifPresent(value -> currentItemList.add(value));
        }

        itemAdapter = new ItemAdapter(this.currentItemList, this);
        recyclerView.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }

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
                this.sortedItemList = itemList.stream()
                        .sorted(Comparator
                                .comparing(Item::getListId)
                                .thenComparing((item1, item2) -> {
                                    if (item1.getName().isEmpty() && item2.getName().isEmpty()) {
                                        return 0;
                                    } else if (item1.getName().isEmpty()) {
                                        return 1;
                                    } else if (item2.getName().isEmpty()) {
                                        return -1;
                                    } else {
                                        return item1.getName().compareTo(item2.getName());
                                    }
                                }))
                        .collect(Collectors.toList());
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

    public void updateData(List<Item> itemList, HashMap<Integer, List<Integer>> listIdMap) {
        if ((itemList == null) || (!(hasNetworkConnection()))) {
            setErrorText();
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        this.itemList = itemList;
        this.listIdMap = listIdMap;
        listIds = this.listIdMap.keySet().stream().sorted().collect(Collectors.toList());
        arrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_item, listIds);
        drawerList.setAdapter(arrayAdapter);
        itemAdapter = new ItemAdapter(this.itemList, this);
        recyclerView.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setErrorText() {
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }
}