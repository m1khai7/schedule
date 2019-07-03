package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.SimpleItemClickListener;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.entity.Calls;


public class DialogSelectLessonFragmentAdapter extends RecyclerView.Adapter<DialogSelectLessonFragmentAdapter.ViewHolder> {

    private ArrayList<Calls> callsList;
    private SimpleItemClickListener simpleItemClickListener;


    public DialogSelectLessonFragmentAdapter (ArrayList<Calls> calls, SimpleItemClickListener simpleItemClickListener) {
        this.callsList=calls;
        this.simpleItemClickListener = simpleItemClickListener;
    }

    @NotNull
    @Override
    public DialogSelectLessonFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_call, parent, false);
        return new DialogSelectLessonFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogSelectLessonFragmentAdapter.ViewHolder holder, final int position) {
        holder.render(position);
    }

    public void setLessonsList(ArrayList<Calls> callsList) {
        this.callsList = callsList;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cardViewCall;
        private final TextView numberCall;
        private final TextView call_timeOne;
        private final TextView call_timeTwo;

        public ViewHolder(View view) {
            super(view);
            cardViewCall = view.findViewById(R.id.call_cardView);
            numberCall = view.findViewById(R.id.call_number);
            call_timeOne = view.findViewById(R.id.call_timeOne);
            call_timeTwo = view.findViewById(R.id.call_timeTwo);
            cardViewCall.setOnClickListener(this);
            call_timeOne.setOnClickListener(this);
            call_timeTwo.setOnClickListener(this);
            view.setOnClickListener(this);

        }

        @SuppressLint("SetTextI18n")
        public void render(int position) {
            callsList = CallDao.getInstance().getAllData();
            numberCall.setText(callsList.get(position).getId());
            call_timeOne.setText(callsList.get(position * 2).getName());
            call_timeTwo.setText(" - " + callsList.get((position * 2) + 1).getName());
        }

        @Override
        public void onClick(View v) {
            simpleItemClickListener.onItemClick(getAdapterPosition(),v);
        }
    }
}
