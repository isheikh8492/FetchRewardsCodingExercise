package com.fetchrewards.codingexercise;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView id;

    TextView listId;

    TextView name;

    TextView nameText;
    ItemViewHolder(View view) {
        super(view);
        id = view.findViewById(R.id.id);
        listId = view.findViewById(R.id.listId);
        name = view.findViewById(R.id.name);
        nameText = view.findViewById(R.id.nameText);
    }
}
