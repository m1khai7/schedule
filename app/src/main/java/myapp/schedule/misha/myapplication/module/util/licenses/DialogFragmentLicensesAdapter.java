package myapp.schedule.misha.myapplication.module.util.licenses;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.entity.Licenses;
import myapp.schedule.misha.myapplication.util.SpannableStringUtil;


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
        holder.text.setText(SpannableStringUtil.getString(listItems.get(position).getText()));
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

