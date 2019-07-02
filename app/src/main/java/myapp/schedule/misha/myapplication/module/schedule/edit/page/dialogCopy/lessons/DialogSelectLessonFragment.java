package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import static myapp.schedule.misha.myapplication.Constants.ITEMS_LIST;


public class DialogSelectLessonFragment extends BaseAlertDialog implements DialogSelectLessonFragmentView {

    private DialogSelectLessonFragmentPresenter presenter;
    private RecyclerView rvItems;
    private DialogSelectLessonFragmentAdapter dialogFragmentListItemsAdapter;

    public static DialogSelectLessonFragment newInstance() {
        return new DialogSelectLessonFragment();
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<Calls> callsList = new ArrayList<>();
        presenter = new DialogSelectLessonFragmentPresenter();

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialog_rv_weeks, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        rvItems = view.findViewById(R.id.rv_dialog_weeks);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
       // updateItemsAdapter(listItems);
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        ArrayList<Calls> itemList = presenter.getItemList();
        dialogFragmentListItemsAdapter.setLessonsList(itemList);
        dialogFragmentListItemsAdapter.notifyDataSetChanged();
        updateItemsAdapter(itemList);
    }

    public void updateItemsAdapter(ArrayList<Calls> itemList) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        int clickedPosition = getArguments().getInt(POSITION);

        dialogFragmentListItemsAdapter = new DialogSelectLessonFragmentAdapter(itemList, (position, view1) -> {
            Intent intent = new Intent();
            intent.putExtra(POSITION, clickedPosition);
            DialogSelectLessonFragment.this.getParentFragment().onActivityResult(fragmentCode, Activity.RESULT_OK, intent);
            DialogSelectLessonFragment.this.dismiss();
        });

        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
