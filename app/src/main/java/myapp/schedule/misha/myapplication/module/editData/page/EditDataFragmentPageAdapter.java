package myapp.schedule.misha.myapplication.module.editData.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.SimpleItemClickListener;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class EditDataFragmentPageAdapter extends RecyclerView.Adapter<EditDataFragmentPageAdapter.ViewHolder> {

    private List<SimpleItem> listItems;

    private SimpleItemClickListener itemClickListener;

    public EditDataFragmentPageAdapter(ArrayList<SimpleItem> items, SimpleItemClickListener simpleItemClickListener) {
        this.listItems = items;
        this.itemClickListener = simpleItemClickListener;
    }

    @Override
    public EditDataFragmentPageAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
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
            SimpleItem simpleItem = listItems.get(position);
            item.setText(simpleItem.getName());

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}

