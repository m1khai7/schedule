package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogCopyFragment extends BaseAlertDialog implements DialogCopyFragmentView {

    private DialogCopyFragmentPresenter presenter;
    private RecyclerView rvItems;
    private DialogCopyFragmentAdapter dialogFragmentListItemsAdapter;

    public static DialogCopyFragment newInstance(Bundle args) {
        DialogCopyFragment fragment = new DialogCopyFragment();
        fragment.setArguments(args);
        return fragment;
    }

   /* @Override
    public void showWeeksDialog(int fragmentCode) {
        DialogFragmentWeeks dialogFragment = null;
        if (fragmentCode == ) {
            dialogFragment = DialogFragmentWeeks.newInstance());
        }
        dialogFragment.show(getChildFragmentManager(), DialogFragmentWeeks.class.getSimpleName());
    }*/


    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        ArrayList<CopyLesson> listItems = getArguments().getParcelableArrayList(ITEMS);
        presenter = new DialogCopyFragmentPresenter(fragmentCode);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialog_copy_lesson, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        rvItems = view.findViewById(R.id.rv_dialog);
        updateItemsAdapter(listItems);
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> presenter.onItemClick(fragmentCode));
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
  //      ArrayList<CopyLesson> itemList = presenter.getItemList();
       // dialogFragmentListItemsAdapter.setLessonList(itemList);
        dialogFragmentListItemsAdapter.notifyDataSetChanged();
      //  updateItemsAdapter(itemList);
    }

    public void updateItemsAdapter(ArrayList<CopyLesson> itemList) {
         dialogFragmentListItemsAdapter = new DialogCopyFragmentAdapter(itemList, (position, view1) -> {
            Toast.makeText(getContext(), "zzz", Toast.LENGTH_SHORT).show();
        });
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
