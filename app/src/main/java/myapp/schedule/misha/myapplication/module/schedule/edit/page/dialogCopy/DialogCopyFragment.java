package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragment;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogCopyFragment extends BaseAlertDialog implements DialogCopyFragmentView, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private DialogCopyFragmentPresenter presenter;
    private RecyclerView rvItems;
    private TextView day;
    private TextView timeLesson;
    private TextView weeks;
    private DialogCopyFragmentAdapter dialogFragmentListItemsAdapter;


    public static DialogCopyFragment newInstance(Bundle args) {
        DialogCopyFragment fragment = new DialogCopyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showLessonDialog() {
        DialogSelectLessonFragment dialogFragment = DialogSelectLessonFragment.newInstance();
        dialogFragment.show(getChildFragmentManager(), DialogSelectLessonFragment.class.getSimpleName());
    }

    @Override
    public void showWeekDialog() {

    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<CopyLesson> listItems = getArguments().getParcelableArrayList(ITEMS);
        presenter = new DialogCopyFragmentPresenter();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_copy_lesson, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        ImageView imageAdd = view.findViewById(R.id.imageAdd);
        rvItems = view.findViewById(R.id.rv_dialog_weeks);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        day = view.findViewById(R.id.day);
        weeks = view.findViewById(R.id.weeks);
        weeks.setOnClickListener(this);
        day.setOnClickListener(this);
        timeLesson = view.findViewById(R.id.timeLesson);
        timeLesson.setText(CallDao.getInstance().getItemByID(1).getName() + " - " + CallDao.getInstance().getItemByID(2).getName());

        updateItemsAdapter(listItems);
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.onItemClick();
            }
        });
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        imageAdd.setOnClickListener(this);
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        //      ArrayList<CopyLesson> itemList = presenter.getItemList();
        // dialogFragmentListItemsAdapter.setLessonList(itemList);
        dialogFragmentListItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateItemsAdapter(ArrayList<CopyLesson> itemList) {
        dialogFragmentListItemsAdapter = new DialogCopyFragmentAdapter(itemList, (position, view1) -> {
            presenter.onImageDeleteClick(itemList, position);
        });
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageAdd) {
            presenter.onImageAddClick(day.getText().toString(), timeLesson.getText().toString());
        }
        if (v.getId() == R.id.weeks) {
            PopupMenu popup = new PopupMenu(v.getContext(), weeks);
            popup.inflate(R.menu.menu_weeks);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }
        if (v.getId() == R.id.day) {
            PopupMenu popup = new PopupMenu(v.getContext(), day);
            popup.inflate(R.menu.menu_days);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }
        if (v.getId() == R.id.timeLesson) {
            presenter.onDialogLessonClick();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            //TODO  WEEKS
            case R.id.selectAll:
                return true;
            case R.id.selectUnevens:
                return true;
            case R.id.selectEvens:
                return true;
            case R.id.selectSelectively:
                return true;
            //TODO  DAYS
            case R.id.monday:
                return true;
            case R.id.tuesday:
                return true;
            case R.id.wednesday:
                return true;
            case R.id.thuesday:
                return true;
            case R.id.friday:
                return true;
            case R.id.saturday:
                return true;
            default:
                return false;
        }

    }
}

