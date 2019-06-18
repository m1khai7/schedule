package com.example.misha.myapplication.module.schedule.edit.page.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.SimpleItemClickListener;
import com.example.misha.myapplication.entity.SimpleItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class DialogFragmentListItemsAdapter extends RecyclerView.Adapter<DialogFragmentListItemsAdapter.ViewHolder> {

    private List<SimpleItem> listItems;

    private SimpleItemClickListener itemClickListener;

    public DialogFragmentListItemsAdapter(ArrayList<SimpleItem> items, SimpleItemClickListener simpleItemClickListener) {
        this.listItems = items;
        this.itemClickListener = simpleItemClickListener;
    }

    @Override
    public DialogFragmentListItemsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    public void setLessonList(List<SimpleItem> lessonList) {
        this.listItems = lessonList;
    }


    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView item;

        private ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.item);
            view.setOnClickListener(this);

        }

        private void onBindView(int position) {
            SimpleItem item = listItems.get(position);
            this.item.setText(item.getName());

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}

