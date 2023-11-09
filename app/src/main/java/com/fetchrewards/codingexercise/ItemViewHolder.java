package com.fetchrewards.codingexercise;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView id;

    TextView listId;

    TextView name;

    ItemViewHolder(View view) {
        super(view);
        id = view.findViewById(R.id.id);
        listId = view.findViewById(R.id.listId);
        name = view.findViewById(R.id.name);
    }
}
