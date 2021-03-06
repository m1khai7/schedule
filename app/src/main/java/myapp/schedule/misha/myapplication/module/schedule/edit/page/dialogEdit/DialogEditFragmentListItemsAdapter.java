package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.SimpleItemClickListener;
import myapp.schedule.misha.myapplication.entity.SimpleItem;


public class DialogEditFragmentListItemsAdapter extends RecyclerView.Adapter<DialogEditFragmentListItemsAdapter.ViewHolder> {

    private List<SimpleItem> listItems;

    private SimpleItemClickListener itemClickListener;

    public DialogEditFragmentListItemsAdapter(ArrayList<SimpleItem> items, SimpleItemClickListener simpleItemClickListener) {
        this.listItems = items;
        this.itemClickListener = simpleItemClickListener;
    }

    @Override
    public DialogEditFragmentListItemsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    public void setLessonList(List<SimpleItem> lessonList) {
        this.listItems = lessonList;
    }


    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        holder.render(position);
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

        private void render(int position) {
            SimpleItem item = listItems.get(position);
            this.item.setText(item.getName());

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}

