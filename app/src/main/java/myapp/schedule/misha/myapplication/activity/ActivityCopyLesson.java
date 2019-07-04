package myapp.schedule.misha.myapplication.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.DialogCopyFragmentAdapter;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons.DialogSelectLessonFragment;

import static myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView.ITEMS;

public class ActivityCopyLesson extends BaseActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private RecyclerView rvItems;
    private TextView day;
    private TextView timeLesson;
    private TextView weeks;
    private DialogCopyFragmentAdapter dialogFragmentListItemsAdapter;
    private ArrayList<CopyLesson> copyLessons = new ArrayList<>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTitle("Скопировать занятие");
        ArrayList<CopyLesson> listItems = (ArrayList<CopyLesson>) getIntent().getSerializableExtra(ITEMS);
        ImageView imageAdd = findViewById(R.id.imageAdd);
        rvItems = findViewById(R.id.rv_dialog_weeks);
        rvItems.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        day = findViewById(R.id.day);
        weeks = findViewById(R.id.weeks);
        weeks.setOnClickListener(this);
        day.setOnClickListener(this);
        timeLesson = findViewById(R.id.timeLesson);
        timeLesson.setOnClickListener(this);
        timeLesson.setText(CallDao.getInstance().getItemByID(1).getName() + " - " + CallDao.getInstance().getItemByID(2).getName());

        updateItemsAdapter(listItems);
        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.onItemClick();
            }
        });
        Button button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> finish());
        imageAdd.setOnClickListener(this);
    }


    public void updateItemsAdapter(ArrayList<CopyLesson> itemList) {
        dialogFragmentListItemsAdapter = new DialogCopyFragmentAdapter(itemList, (position, view1) -> {
            onImageDeleteClick(itemList, position);
        });
        rvItems.setAdapter(dialogFragmentListItemsAdapter);
    }

    public void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position) {
        itemList.remove(position);
        updateItemsAdapter(itemList);
    }


    public void onImageAddClick(String day, String timeLesson) {
        CopyLesson copyLesson = new CopyLesson();
        copyLesson.setDay(day);
        copyLesson.setTimeLesson(timeLesson);
        copyLessons.add(copyLesson);
        updateItemsAdapter(copyLessons);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageAdd) {
            onImageAddClick(day.getText().toString(), timeLesson.getText().toString());
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
            showLessonDialog();
        }
    }

    public void showLessonDialog() {
        DialogSelectLessonFragment dialogFragment = DialogSelectLessonFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), DialogSelectLessonFragment.class.getSimpleName());
    }

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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }
}