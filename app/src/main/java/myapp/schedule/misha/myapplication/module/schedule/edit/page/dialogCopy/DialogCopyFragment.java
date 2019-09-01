package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapp.schedule.misha.myapplication.Constants;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Weeks;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragmentView;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragmentView;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogCopyFragment extends BaseMainFragment implements DialogCopyFragmentView,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private DialogCopyFragmentPresenter presenter;

    private ArrayList<Weeks> listWeeks = new ArrayList<>();

    private ArrayList<CopyLesson> listLessonsForCopy = new ArrayList<>();

    private ArrayList<Weeks> listWeeksSelected = new ArrayList<>();

    private RecyclerView rvItems;

    private Spinner spinnerDay;

    private TextView tvLesson;

    private TextView tvWeeks;

    private PopupMenu popupMenu;

    private int lessonPosition;

    public static DialogCopyFragment newInstance(Lesson lesson) {
        Bundle args = new Bundle();
        args.putParcelable(DialogCopyFragmentView.CURRENT_LESSON, lesson);
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
    public void openWeekDialog() {
        DialogSelectWeekFragment dialogFragment = DialogSelectWeekFragment.newInstance(listWeeks);
        dialogFragment.show(getChildFragmentManager(), DialogSelectWeekFragment.class.getSimpleName());
    }

    //TODO replace dialog to fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DialogCopyFragmentPresenter();
        setAllWeeks();
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_copy_lesson, null);
        setView(view);
        setListeners(view);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List<String> listDays = Arrays.asList(getResources().getStringArray(R.array.days));
        spinnerAdapter.addAll(listDays);
        spinnerDay.setAdapter(spinnerAdapter);
        spinnerDay.setSelection(0);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        tvLesson.setText(ScheduleApp.getStr(R.string.timelesson, CallDao.getInstance().getItemByID(1).getName(), CallDao.getInstance().getItemByID(2).getName()));
        listWeeks = new Weeks().getNewListWeeks();
        return view;
    }

    private void setView(View view) {
        spinnerDay = view.findViewById(R.id.educator);
        rvItems = view.findViewById(R.id.rv_dialog_list);
        tvWeeks = view.findViewById(R.id.weeks);
        tvLesson = view.findViewById(R.id.audience);
    }

    private void setListeners(View view) {
        popupMenu = new PopupMenu(view.getContext(), tvWeeks);
        popupMenu.inflate(R.menu.menu_weeks);
        popupMenu.setOnMenuItemClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> presenter.onClickCopyLesson());
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> getActivity().finish());
        view.findViewById(R.id.imageAdd).setOnClickListener(this);
        tvWeeks.setOnClickListener(this);
        tvLesson.setOnClickListener(this);
    }

    @Override
    public void copyLesson() {
        if (listLessonsForCopy.isEmpty()) {
            showError(R.string.empty_list_selected_day);
        } else {
            Lesson currentLesson = getArguments().getParcelable(DialogCopyFragmentView.CURRENT_LESSON);
            ArrayList<Lesson> lessonListWeek;
            for (Weeks weeks : listWeeksSelected) {
                for (CopyLesson lesson : listLessonsForCopy) {
                    lessonListWeek = LessonDao.getInstance().getLessonByWeekAndDay(weeks.getNumber(), lesson.getDay() + 1);
                    lessonListWeek.get(lesson.getId()).setData(currentLesson.getId_subject(), currentLesson.getId_audience(),
                            currentLesson.getId_educator(), currentLesson.getId_typelesson());
                    LessonDao.getInstance().updateItemByID(lessonListWeek.get(lesson.getId()));
                }
                getActivity().finish();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void updateItemsAdapter(ArrayList<CopyLesson> listLessons) {
        DialogCopyFragmentAdapter dialogFragmentListItemsAdapter = new DialogCopyFragmentAdapter(listLessons, (position, view1) ->
                presenter.onImageDeleteClick(listLessons, position));
        listLessonsForCopy = listLessons;
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        if (requestCode == DialogCopyFragmentView.LESSON) {
            lessonPosition = data.getIntExtra(DialogSelectLessonFragmentView.POSITION, 0);
            ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
            tvLesson.setText(ScheduleApp.getStr(R.string.timelesson, callsList.get(lessonPosition * 2).getName(),
                    callsList.get((lessonPosition * 2) + 1).getName()));
        }
        if (requestCode == DialogSelectWeekFragmentView.LIST_ITEMS) {
            listWeeks = new ArrayList<>();
            listWeeksSelected = new ArrayList<>();
            listWeeks = data.getParcelableArrayListExtra(Constants.LIST_ITEMS);
            String prefix = "";
            StringBuilder stringSelectedWeeks = new StringBuilder();
            for (int i = 0; i < listWeeks.size(); i++) {
                Weeks week = listWeeks.get(i);
                week.setNumber(i + 1);
                if (week.isChecked()) {
                    stringSelectedWeeks.append(prefix);
                    prefix = ", ";
                    stringSelectedWeeks.append(i + 1);
                }
                if (week.isChecked()) listWeeksSelected.add(week);
            }
            if (listWeeksSelected.size() == listWeeks.size() || listWeeksSelected.isEmpty()) {
                tvWeeks.setText(R.string.select_all);
            } else {
                tvWeeks.setText(stringSelectedWeeks);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageAdd) {
            presenter.onImageAddClick(lessonPosition, spinnerDay.getSelectedItemPosition(), tvLesson.getText().toString());
        }
        if (v.getId() == R.id.weeks) {
            popupMenu.show();
        }
        if (v.getId() == R.id.educator) {
            PopupMenu popup = new PopupMenu(v.getContext(), spinnerDay);
            popup.inflate(R.menu.menu_days);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }
        if (v.getId() == R.id.audience) {
            presenter.onDialogLessonClick();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSelectAll)
            tvWeeks.setText(R.string.select_all);
        setAllWeeks();
        if (id == R.id.menuSelectUnevens)
            tvWeeks.setText(R.string.select_unevens);
        if (id == R.id.menuSelectEvens)
            tvWeeks.setText(R.string.select_evens);
        if (id == R.id.menuSelectively) {
            tvWeeks.setText(R.string.select_all);
            presenter.showWeeks();
        }
        return true;
    }

    private void setAllWeeks() {
        ArrayList<Weeks> AllWeeks = new ArrayList<>();
        for (int i = 1; i < 18; i++) {
            Weeks week = new Weeks();
            week.setNumber(i);
            week.setCheck(true);
            AllWeeks.add(week);
        }
        listWeeksSelected = AllWeeks;
    }

    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }
}

