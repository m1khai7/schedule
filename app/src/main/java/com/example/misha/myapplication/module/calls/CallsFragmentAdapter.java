package com.example.misha.myapplication.module.calls;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.entity.Calls;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CallsFragmentAdapter extends RecyclerView.Adapter<CallsFragmentAdapter.ViewHolder> {


    private ArrayList<Calls> callsList = new ArrayList<>();
    private CallsPresenter itemClickListener;


    public CallsFragmentAdapter(CallsPresenter callsScheduleCallback) {
        this.itemClickListener = callsScheduleCallback;
    }

    @NotNull
    @Override
    public CallsFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_call, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.onBindView(position);
    }

    public void setCallsList(ArrayList<Calls> callsList) {
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
        public void onBindView(int position) {
            callsList = CallDao.getInstance().getAllData();
            numberCall.setText(callsList.get(position).getId());
            call_timeOne.setText(callsList.get(position * 2).getName());
            call_timeTwo.setText(" - " + callsList.get((position * 2) + 1).getName());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.call_timeOne) {
                itemClickListener.onClickOneTime(getAdapterPosition());
            }
            if (v.getId() == R.id.call_timeTwo) {
                itemClickListener.onClickTwoTime(getAdapterPosition());
            }
        }
    }
}