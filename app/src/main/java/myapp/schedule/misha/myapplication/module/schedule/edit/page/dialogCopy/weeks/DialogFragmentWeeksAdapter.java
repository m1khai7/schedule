package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.SimpleItemClickListener;
import myapp.schedule.misha.myapplication.entity.SimpleItem;


public class DialogFragmentWeeksAdapter extends RecyclerView.Adapter<DialogFragmentWeeksAdapter.ViewHolder> {

    private List<SimpleItem> listItems;

    private SimpleItemClickListener itemClickListener;

    public DialogFragmentWeeksAdapter(ArrayList<SimpleItem> items, SimpleItemClickListener simpleItemClickListener) {
        this.listItems = items;
        this.itemClickListener = simpleItemClickListener;
    }

    @Override
    public DialogFragmentWeeksAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_week, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private final TextView textViewWeek;
        private final CheckBox checkBoxSelected;

        private ViewHolder(View view) {
            super(view);
            textViewWeek = view.findViewById(R.id.textViewWeek);
            checkBoxSelected = view.findViewById(R.id.checkBoxSelected);
            checkBoxSelected.setOnCheckedChangeListener(this);
        }

        private void onBindView(int position) {
            SimpleItem item = listItems.get(position);
            this.textViewWeek.setText(item.getName());

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            itemClickListener.onItemClick(getAdapterPosition(), buttonView);
        }
    }
}

