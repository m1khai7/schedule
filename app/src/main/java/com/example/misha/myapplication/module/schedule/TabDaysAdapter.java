package com.example.misha.myapplication.module.schedule;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.ScheduleApp;
import com.example.misha.myapplication.SimpleItemClickListener;
import com.example.misha.myapplication.data.preferences.Preferences;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;


public class TabDaysAdapter extends RecyclerView.Adapter<TabDaysAdapter.ViewHolder> {

    private int selectedPos;
    private SimpleItemClickListener callback;
    private ArrayList<String> dayYear = new ArrayList<>();

    public TabDaysAdapter(SimpleItemClickListener simpleItemClickListener) {
        this.callback = simpleItemClickListener;
        updateData(Preferences.getInstance().getSelectedWeekSchedule());
    }

    public void updateData(int selectedPos) {
        dayYear.clear();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(Long.valueOf(Preferences.getInstance().getSemestrStart()));
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mFormatDay = new SimpleDateFormat("dd EEE");
        mCalendar.add(Calendar.WEEK_OF_YEAR, selectedPos);
        for (int day = 0; day < 6; day++) {
            dayYear.add(mFormatDay.format(mCalendar.getTime()).toUpperCase());
            mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tab_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public void setSelection(int position) {
        selectedPos = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView date;


        private ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            date = view.findViewById(R.id.date);
        }

        private void onBindView(int position) {
            date.setText(dayYear.get(position));
            String nameTheme = Preferences.getInstance().getSelectedTheme();
            if (nameTheme.equals(DARK_THEME)) {
                itemView.setBackgroundColor(selectedPos == position ? ScheduleApp.getClr(R.color.colorAccent) : Color.TRANSPARENT);
            }
            if (nameTheme.equals(LIGHT_THEME)) {
                itemView.setBackgroundColor(selectedPos == position ? ScheduleApp.getClr(R.color.green) : Color.TRANSPARENT);
            }

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            callback.onItemClick(clickedPosition, v);
        }
    }
}