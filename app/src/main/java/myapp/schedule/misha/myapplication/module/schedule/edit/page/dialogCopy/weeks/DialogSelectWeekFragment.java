package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.Constants;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.Weeks;


public class DialogSelectWeekFragment extends BaseAlertDialog implements DialogSelectWeekFragmentView {

    private DialogSelectWeekFragmentPresenter presenter;

    private DialogSelectWeekFragmentAdapter dialogFragmentListItemsAdapter;

    private ArrayList<Weeks> listWeeks = new ArrayList<>();

    public static DialogSelectWeekFragment newInstance(ArrayList<Weeks> listWeeks) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.LIST_ITEMS, listWeeks);
        DialogSelectWeekFragment fragment = new DialogSelectWeekFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        presenter = new DialogSelectWeekFragmentPresenter();
        dialogFragmentListItemsAdapter = new DialogSelectWeekFragmentAdapter();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        listWeeks = getArguments().getParcelableArrayList(Constants.LIST_ITEMS);
        View view = layoutInflater.inflate(R.layout.dialog_rv_weeks, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        RecyclerView rvItems = view.findViewById(R.id.rv_dialog_list);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
        updateAdapter();
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            int countUnselectedWeek = 0;
            for (Weeks week : listWeeks) {
                if (!week.isChecked()) countUnselectedWeek += 1;
            }
            if (listWeeks.size() == countUnselectedWeek) {
                showError(R.string.error_selected_week);
            } else {
                listWeeks = dialogFragmentListItemsAdapter.getListWeeks();
                Intent intent = new Intent();
                intent.putExtra(Constants.LIST_ITEMS, listWeeks);
                getParentFragment().onActivityResult(DialogSelectWeekFragmentView.LIST_ITEMS, Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.btn_select_all).setOnClickListener(v -> presenter.onSelectAllClicked());
        return builder.create();
    }

    public void selectAll() {
        for (Weeks week : listWeeks) {
            week.setCheck(getCheckOnFullSelect());
        }
        dialogFragmentListItemsAdapter.setListWeeks(listWeeks);
    }

    private Boolean getCheckOnFullSelect() {
        boolean checkOnFullSelect = false;
        for (Weeks week : listWeeks) {
            checkOnFullSelect = !week.isChecked();
        }
        return checkOnFullSelect;
    }

    public void updateAdapter() {
        dialogFragmentListItemsAdapter.setListWeeks(listWeeks);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
