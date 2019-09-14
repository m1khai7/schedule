package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.SimpleItemClickListener;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;


public class CopyFragmentAdapter extends RecyclerView.Adapter<CopyFragmentAdapter.ViewHolder> {

    private ArrayList<CopyLesson> listItems;

    private SimpleItemClickListener itemClickListener;

    public CopyFragmentAdapter(ArrayList<CopyLesson> listLessons, SimpleItemClickListener simpleItemClickListener) {
        this.listItems = listLessons;
        this.itemClickListener = simpleItemClickListener;
    }

    @Override
    public CopyFragmentAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_copy_lesson, parent, false);
        return new ViewHolder(view);
    }

    public void setListItems(ArrayList<CopyLesson> listLessons) {
        this.listItems = listLessons;
        notifyDataSetChanged();
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

        private final TextView day;
        private final TextView timeLesson;

        private ViewHolder(View view) {
            super(view);
            day = view.findViewById(R.id.day);
            timeLesson = view.findViewById(R.id.timeLesson);
            ImageView imageDelete = view.findViewById(R.id.imageDelete);
            imageDelete.setOnClickListener(this);
            if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
                imageDelete.setImageResource(R.drawable.ic_delete_white);
            } else {
                imageDelete.setImageResource(R.drawable.ic_delete_black);
            }
        }

        private String getDay(int position) {
            List<String> listDays = Arrays.asList(ScheduleApp.getAppContext().getResources().getStringArray(R.array.days));
            return listDays.get(position);
        }

        private void render(int position) {
            CopyLesson item = listItems.get(position);
            day.setText(getDay(item.getDay()));
            timeLesson.setText(item.getTimeLesson());
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}

