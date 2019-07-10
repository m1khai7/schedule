package myapp.schedule.misha.myapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.module.container.Container;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.DialogCopyFragmentAdapter;

public class ActivityCopyLesson extends BaseActivity {

    private final Container container = Container.newInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTitle("Скопировать занятие");



    }
/*

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


    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        super.onActivityResult(requestCode, resultOk, data);
        setText(data.getIntExtra(POSITION, 0));
    }

    public void setText(int position) {
        TextView tvLesson = findViewById(R.id.timeLesson);
        ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
        tvLesson.setText(callsList.get(position * 2).getName() + " - " + callsList.get((position * 2) + 1).getName());
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
                weeks.setText("Все недели");
                return true;
            case R.id.selectUnevens:
                weeks.setText("Нечетные недели");
                return true;
            case R.id.selectEvens:
                weeks.setText("Четные недели");
                return true;
            case R.id.selectSelectively:

                return true;
            //TODO  DAYS
            case R.id.monday:
                day.setText("Понедельник");
                return true;
            case R.id.tuesday:
                day.setText("Вторник");
                return true;
            case R.id.wednesday:
                day.setText("Среда");
                return true;
            case R.id.thuesday:
                day.setText("Четверг");
                return true;
            case R.id.friday:
                day.setText("Пятница");
                return true;
            case R.id.saturday:
                day.setText("Суббота");
                return true;
            default:
                return false;
        }

    }
*/


    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }
}