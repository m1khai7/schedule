package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

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
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import static myapp.schedule.misha.myapplication.Constants.ITEMS_LIST;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogFragmentWeeks extends BaseAlertDialog implements DialogFragmentWeeksView {

    private DialogFragmentWeeksPresenter presenter;
    private RecyclerView rvItems;
    private DialogFragmentWeeksAdapter dialogFragmentListItemsAdapter;

    public static DialogFragmentWeeks newInstance(Bundle args) {
        DialogFragmentWeeks fragment = new DialogFragmentWeeks();
        fragment.setArguments(args);
        return fragment;
    }


    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        ArrayList<SimpleItem> listItems = getArguments().getParcelableArrayList(ITEMS);
        presenter = new DialogFragmentWeeksPresenter(fragmentCode);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialog_rv_list, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        rvItems = view.findViewById(R.id.rv_dialog);
        updateItemsAdapter(listItems);
        Button buttonAllSelect = view.findViewById(R.id.button_ok);
        buttonAllSelect.setOnClickListener(v -> presenter.onItemClick(fragmentCode));
        Button buttonAllDeselect = view.findViewById(R.id.button_ok);
        buttonAllDeselect.setOnClickListener(v -> presenter.onItemClick(fragmentCode));
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> presenter.onItemClick(fragmentCode));
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        ArrayList<SimpleItem> itemList = presenter.getItemList();
        dialogFragmentListItemsAdapter.setLessonList(itemList);
        dialogFragmentListItemsAdapter.notifyDataSetChanged();
        updateItemsAdapter(itemList);
    }

    public void updateItemsAdapter(ArrayList<SimpleItem> subjectList) {
        int fragmentCode = getArguments().getInt(FRAGMENT_CODE);
        int clickedPosition = getArguments().getInt(POSITION);

        dialogFragmentListItemsAdapter = new DialogFragmentWeeksAdapter(subjectList, (position, view1) -> {
            Intent intent = new Intent();
            intent.putExtra(POSITION, clickedPosition);
            intent.putExtra(ITEMS_LIST, subjectList.get(position));
            DialogFragmentWeeks.this.getParentFragment().onActivityResult(fragmentCode, Activity.RESULT_OK, intent);
            DialogFragmentWeeks.this.dismiss();
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
