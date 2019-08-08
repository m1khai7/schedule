package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragmentView;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogCopyFragment extends BaseMainFragment implements DialogCopyFragmentView, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private DialogCopyFragmentPresenter presenter;
    private RecyclerView rvItems;
    private TextView tvDay;
    private TextView tvLesson;
    private TextView tvWeeks;

    public static DialogCopyFragment newInstance() {
        return new DialogCopyFragment();
    }

    @Override
    public void showLessonDialog() {
        DialogSelectLessonFragment dialogFragment = DialogSelectLessonFragment.newInstance();
        dialogFragment.show(getChildFragmentManager(), DialogSelectLessonFragment.class.getSimpleName());
    }

    @Override
    public void showWeekDialog() {

    }

    //TODO replace dialog to fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DialogCopyFragmentPresenter();
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_copy_lesson, null);
        ImageView imageAdd = view.findViewById(R.id.imageAdd);
        rvItems = view.findViewById(R.id.rv_dialog_weeks);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        tvDay = view.findViewById(R.id.day);
        tvWeeks = view.findViewById(R.id.weeks);
        tvWeeks.setOnClickListener(this);
        tvDay.setOnClickListener(this);
        tvLesson = view.findViewById(R.id.timeLesson);
        tvLesson.setOnClickListener(this);
        tvLesson.setText(ScheduleApp.getStr(R.string.timelesson, CallDao.getInstance().getItemByID(1).getName(), CallDao.getInstance().getItemByID(2).getName()));

        //   updateItemsAdapter(listItems);
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            //presenter.onItemClick();
        });
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> getActivity().finish());
        imageAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void updateItemsAdapter(ArrayList<CopyLesson> itemList) {
        DialogCopyFragmentAdapter dialogFragmentListItemsAdapter = new DialogCopyFragmentAdapter(itemList, (position, view1) -> {
            presenter.onImageDeleteClick(itemList, position);
        });
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        if (requestCode == DialogCopyFragmentView.SELECT_LESSON) {
            int lessonPosition = data.getIntExtra(DialogSelectLessonFragmentView.POSITION, 0);
            ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
            tvLesson.setText(ScheduleApp.getStr(R.string.timelesson, callsList.get(lessonPosition * 2).getName(),
                    callsList.get((lessonPosition * 2) + 1).getName()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageAdd) {
            presenter.onImageAddClick(tvDay.getText().toString(), tvLesson.getText().toString());
        }
        if (v.getId() == R.id.weeks) {
            PopupMenu popup = new PopupMenu(v.getContext(), tvWeeks);
            popup.inflate(R.menu.menu_weeks);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }
        if (v.getId() == R.id.day) {
            PopupMenu popup = new PopupMenu(v.getContext(), tvDay);
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
            case R.id.menuSelectAll:
                tvWeeks.setText(R.string.select_all);
                return true;
            case R.id.menuSelectUnevens:
                tvWeeks.setText(R.string.select_unevens);
                return true;
            case R.id.menuSelectEvens:
                tvWeeks.setText(R.string.select_evens);
                return true;
            case R.id.menuSelectively:
                tvWeeks.setText(R.string.selectively);
                return true;
            //TODO  DAYS
            case R.id.monday:
                tvDay.setText(R.string.monday);
                return true;
            case R.id.tuesday:
                tvDay.setText(R.string.tuesday);
                return true;
            case R.id.wednesday:
                tvDay.setText(R.string.wednesday);
                return true;
            case R.id.thuesday:
                tvDay.setText(R.string.thuesday);
                return true;
            case R.id.friday:
                tvDay.setText(R.string.friday);
                return true;
            case R.id.saturday:
                tvDay.setText(R.string.saturday);
                return true;
            default:
                return false;
        }

    }

    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }
}

