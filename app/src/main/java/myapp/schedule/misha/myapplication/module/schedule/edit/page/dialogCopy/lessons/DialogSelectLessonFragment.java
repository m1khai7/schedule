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
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.DialogCopyFragmentView;


public class DialogSelectLessonFragment extends BaseAlertDialog implements DialogSelectLessonFragmentView {

    private DialogSelectLessonFragmentPresenter presenter;

    private RecyclerView rvItems;

    public static DialogSelectLessonFragment newInstance() {
        return new DialogSelectLessonFragment();
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
        presenter = new DialogSelectLessonFragmentPresenter();

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialog_rv_lessons, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        rvItems = view.findViewById(R.id.rv_dialog_lessons);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        updateItemsAdapter(callsList);
        Button button_cancel = view.findViewById(R.id.btn_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }

    public void updateItemsAdapter(ArrayList<Calls> itemList) {
        DialogSelectLessonFragmentAdapter dialogFragmentListItemsAdapter = new DialogSelectLessonFragmentAdapter(itemList, (position, view1) -> {
            Intent intent = new Intent();
            intent.putExtra(DialogSelectLessonFragmentView.POSITION, position);
            getParentFragment().onActivityResult(DialogCopyFragmentView.SELECT_LESSON, Activity.RESULT_OK, intent);
            dismiss();
        });
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
