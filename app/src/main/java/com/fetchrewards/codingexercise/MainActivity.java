package com.fetchrewards.codingexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<Item> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

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

        // Updated to include border width and corner radius
        BorderItemDecoration borderItemDecoration = new BorderItemDecoration(this);
        recyclerView.addItemDecoration(borderItemDecoration);

        // Populate the list and notify the adapter
        for (int i = 0; i < 10; i++) {
            Item item = new Item(1, (5 - i), "Test");
            itemList.add(item);
        }
        itemAdapter.notifyDataSetChanged();
    }

}