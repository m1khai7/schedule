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

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.entity.Weeks;


public class DialogSelectWeekFragmentAdapter extends RecyclerView.Adapter<DialogSelectWeekFragmentAdapter.ViewHolder> {

    private ArrayList<Weeks> listWeeks = new ArrayList<>();

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_week, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        holder.render(position);
    }

    public void setListWeeks(ArrayList<Weeks> arrayListWeeks) {
        this.listWeeks = arrayListWeeks;
        notifyDataSetChanged();
    }

    public ArrayList<Weeks> getListWeeks() {
        return listWeeks;
    }

    @Override
    public int getItemCount() {
        return listWeeks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        private final TextView textViewWeek;
        private final CheckBox checkBoxSelected;

        private ViewHolder(View view) {
            super(view);
            textViewWeek = view.findViewById(R.id.textViewWeek);
            checkBoxSelected = view.findViewById(R.id.checkBoxSelected);
            itemView.setOnClickListener(this);
            checkBoxSelected.setOnCheckedChangeListener(this);
        }

        private void render(int position) {
            textViewWeek.setText(listWeeks.get(position).getName());
            checkBoxSelected.setChecked(listWeeks.get(position).isChecked());
        }

        @Override
        public void onClick(View view) {
            if (view.getId() != R.id.checkBoxSelected) {
                if (checkBoxSelected.isChecked()) {
                    checkBoxSelected.setChecked(false);
                    listWeeks.get(getAdapterPosition()).setCheck(false);
                } else {
                    checkBoxSelected.setChecked(true);
                    listWeeks.get(getAdapterPosition()).setCheck(true);
                }
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            listWeeks.get(getAdapterPosition()).setCheck(b);
        }
    }
}

