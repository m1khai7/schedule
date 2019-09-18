package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Weeks;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragmentView;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragmentView;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;

public class CopyFragment extends BaseMainFragment implements CopyFragmentView,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private CopyFragmentPresenter presenter;

    private ArrayList<Weeks> listWeeks = new ArrayList<>();

    private ArrayList<CopyLesson> listLessonsForCopy = new ArrayList<>();

    private CopyFragmentAdapter adapter;

    private ArrayList<Weeks> listWeeksSelected = new ArrayList<>();

    private RecyclerView rvItems;

    private Spinner spinnerDay;

    private TextView tvLesson;

    private TextView tvWeeks;

    private RelativeLayout relLayWeeks;

    private ImageView imageAdd;

    private int lessonPosition;

    public static CopyFragment newInstance(Lesson lesson) {
        Bundle args = new Bundle();
        args.putParcelable(CopyFragmentView.CURRENT_LESSON, lesson);
        CopyFragment fragment = new CopyFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        getContext().setTitle(R.string.title_copy_lesson);
        if (getParent() != null) {
            showIcon();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CopyFragmentPresenter();
        setAllWeeks();
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_copy_lesson, null);
        setView(view);
        setListeners(view);
        setThemeColorViews();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List<String> listDays = Arrays.asList(getResources().getStringArray(R.array.days));
        spinnerAdapter.addAll(listDays);
        spinnerDay.setAdapter(spinnerAdapter);
        spinnerDay.setSelection(0);
        adapter = new CopyFragmentAdapter(listLessonsForCopy, (position, view1) -> presenter.onImageDeleteClick(listLessonsForCopy, position));
        rvItems.setAdapter(adapter);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        tvLesson.setText(ScheduleApp.getStr(R.string.timelesson, CallDao.getInstance().getItemByID(1).getName(), CallDao.getInstance().getItemByID(2).getName()));
        listWeeks = new Weeks().getNewListWeeks();
        return view;
    }

    private void setThemeColorViews() {
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            imageAdd.setImageResource(R.drawable.ic_add_white);
        } else {
            imageAdd.setImageResource(R.drawable.ic_add_black);
        }
    }

    private void setView(View view) {
        spinnerDay = view.findViewById(R.id.day);
        rvItems = view.findViewById(R.id.rv_dialog_list);
        relLayWeeks = view.findViewById(R.id.relLayWeeks);
        tvWeeks = view.findViewById(R.id.weeks);
        tvLesson = view.findViewById(R.id.timeLesson);
        imageAdd = view.findViewById(R.id.imageAdd);
    }

    private void setListeners(View view) {
        relLayWeeks.setOnClickListener(this);
        tvLesson.setOnClickListener(this);
        imageAdd.setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> presenter.onClickCopyLesson());
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> getActivity().finish());
    }

    @Override
    public void copyLesson() {
        if (listLessonsForCopy.isEmpty()) {
            showError(R.string.empty_list_selected_day);
        } else {
            Lesson currentLesson = getArguments().getParcelable(CopyFragmentView.CURRENT_LESSON);
            ArrayList<Lesson> lessonListWeek;
            for (Weeks weeks : listWeeksSelected) {
                for (CopyLesson lesson : listLessonsForCopy) {
                    lessonListWeek = LessonDao.getInstance().getLessonByWeekAndDay(weeks.getNumber(), lesson.getDay() + 1);
                    lessonListWeek.get(lesson.getId()).setData(currentLesson.getId_subject(), currentLesson.getId_audience(),
                            currentLesson.getId_educator(), currentLesson.getId_typelesson());
                    LessonDao.getInstance().updateItemByID(lessonListWeek.get(lesson.getId()));
                }
            }
            getActivity().finish();
        }
    }

    @Override
    public void updateItemsAdapter(ArrayList<CopyLesson> listLessons) {
        this.listLessonsForCopy = listLessons;
        adapter.setListItems(listLessons);
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        if (requestCode == CopyFragmentView.LESSON) {
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
                setAllWeeks();
            } else {
                tvWeeks.setText(stringSelectedWeeks);
            }
        }
    }

    private void getPopupMenu(Context context, View view, int id) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(id);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageAdd) {
            presenter.onImageAddClick(lessonPosition, spinnerDay.getSelectedItemPosition(), tvLesson.getText().toString());
        }
        if (v.getId() == R.id.relLayWeeks) {
            getPopupMenu(v.getContext(), relLayWeeks, R.menu.menu_weeks);
        }
        if (v.getId() == R.id.day) {
            getPopupMenu(v.getContext(), spinnerDay, R.menu.menu_days);
        }
        if (v.getId() == R.id.timeLesson) {
            presenter.onDialogLessonClick();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSelectAll) {
            tvWeeks.setText(R.string.select_all);
            setAllWeeks();
        }
        if (id == R.id.menuSelectUnevens) {
            tvWeeks.setText(R.string.select_unevens);
            setWeeks(getString(R.string.select_unevens));
        }
        if (id == R.id.menuSelectEvens) {
            tvWeeks.setText(R.string.select_evens);
            setWeeks(getString(R.string.select_evens));
        }
        if (id == R.id.menuSelectively) {
            tvWeeks.setText(R.string.select_all);
            presenter.showWeeks();
        }
        return true;
    }

    private void setAllWeeks() {
        ArrayList<Weeks> AllWeeks = new ArrayList<>();
        for (int numberWeek = 1; numberWeek < 18; numberWeek++) {
            Weeks week = new Weeks();
            week.setNumber(numberWeek);
            week.setCheck(true);
            AllWeeks.add(week);
        }
        listWeeksSelected = AllWeeks;
    }

    private void setWeeks(String labelWeeks) {
        ArrayList<Weeks> AllWeeks = new ArrayList<>();
        for (int numberWeek = labelWeeks.equals(getString(R.string.select_unevens)) ? 1 : 2;
             numberWeek < 18; numberWeek += 2) {
            Weeks week = new Weeks();
            week.setNumber(numberWeek);
            week.setCheck(true);
            AllWeeks.add(week);
        }
        listWeeksSelected = AllWeeks;
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}

