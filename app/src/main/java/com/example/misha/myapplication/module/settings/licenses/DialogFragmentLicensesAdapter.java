package com.example.misha.myapplication.module.settings.licenses;

import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.entity.Licenses;
import com.example.misha.myapplication.util.StringFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DialogFragmentLicensesAdapter extends RecyclerView.Adapter<DialogFragmentLicensesAdapter.ViewHolder> {

    private ArrayList<Licenses> listItems;

    public DialogFragmentLicensesAdapter(ArrayList<Licenses> licensesArrayList) {
        this.listItems = licensesArrayList;
    }

    @Override
    public DialogFragmentLicensesAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_license, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        holder.title.setText(listItems.get(position).getTitle());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(listItems.get(position).getText());
        spannableStringBuilder = new StringFormatter().getUrlLink(spannableStringBuilder);
        spannableStringBuilder = new StringFormatter().getEmail(spannableStringBuilder);
        holder.text.setText(spannableStringBuilder);
        holder.text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView text;

        private ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.titleText);
            text = itemView.findViewById(R.id.text);
        }
    }
}

