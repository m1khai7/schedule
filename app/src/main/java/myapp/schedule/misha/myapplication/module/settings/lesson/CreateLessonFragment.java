package myapp.schedule.misha.myapplication.module.settings.lesson;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.CopyFragment;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.DialogEditFragmentListItems;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;
import static myapp.schedule.misha.myapplication.Constants.LIST_ITEMS;
import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView.FRAGMENT_CODE;
import static myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView.ITEMS;
import static myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView.POSITION;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class CreateLessonFragment extends BaseMainFragment implements CreateLessonView,
        View.OnClickListener {

    private CreateLessonPresenter presenter;

    private EditText textSubject;
    private EditText textAudience;
    private EditText textEducator;
    private EditText textTypelesson;
    private ImageView imageSubject;
    private ImageView imageAudience;
    private ImageView imageEducator;
    private ImageView imageTypelesson;
    private ImageView imageSelectSubject;
    private ImageView imageSelectAudience;
    private ImageView imageSelectEducator;
    private ImageView imageSelectTypelesson;
    private Button btnNext;
    private Lesson lesson;


    public static CreateLessonFragment newInstance() {
        return new CreateLessonFragment();
    }


    @Override
    public void onPause() {
        super.onPause();
        getContext().setTitle(R.string.title_create_lesson);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreateLessonPresenter();
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_lesson, null);
        setView(view);
        setListeners();
        setThemeColorViews();
        return view;
    }

    @Override
    public void showEditDialog(ArrayList<? extends SimpleItem> itemList, int fragmentCode) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ITEMS, itemList);
        args.putInt(POSITION, 0);
        args.putInt(FRAGMENT_CODE, fragmentCode);
        DialogEditFragmentListItems dialogFragment = DialogEditFragmentListItems.newInstance(args);
        dialogFragment.show(getChildFragmentManager(), DialogEditFragmentListItems.class.getSimpleName());
    }

    @Override
    public void showCopyLesson(Lesson lesson) {
        replaceFragment(CopyFragment.newInstance(lesson));
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        if (requestCode == FRAGMENT_SUBJECTS) {
            Subject subject = data.getParcelableExtra(LIST_ITEMS);
            textSubject.setText(subject.getName());
        }
        if (requestCode == FRAGMENT_TYPELESSONS) {
            Typelesson typelesson = data.getParcelableExtra(LIST_ITEMS);
            textTypelesson.setText(typelesson.getName());
        }
        if (requestCode == FRAGMENT_AUDIENCES) {
            Audience audience = data.getParcelableExtra(LIST_ITEMS);
            textAudience.setText(audience.getName());
        }
        if (requestCode == FRAGMENT_EDUCATORS) {
            Educator educator = data.getParcelableExtra(LIST_ITEMS);
            textEducator.setText(educator.getName());
        }
    }


    private void setThemeColorViews() {
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            imageSubject.setImageResource(R.drawable.ic_subject_white);
            imageAudience.setImageResource(R.drawable.ic_audience_white);
            imageEducator.setImageResource(R.drawable.ic_educator_white);
            imageTypelesson.setImageResource(R.drawable.ic_typelesson_white);
            imageSelectSubject.setImageResource(R.drawable.ic_down_arrow_white);
            imageSelectAudience.setImageResource(R.drawable.ic_down_arrow_white);
            imageSelectEducator.setImageResource(R.drawable.ic_down_arrow_white);
            imageSelectTypelesson.setImageResource(R.drawable.ic_down_arrow_white);
        } else {
            imageSubject.setImageResource(R.drawable.ic_subject_black);
            imageAudience.setImageResource(R.drawable.ic_audience_black);
            imageEducator.setImageResource(R.drawable.ic_educator_black);
            imageTypelesson.setImageResource(R.drawable.ic_typelesson_black);
            imageSelectSubject.setImageResource(R.drawable.ic_down_arrow_black);
            imageSelectAudience.setImageResource(R.drawable.ic_down_arrow_black);
            imageSelectEducator.setImageResource(R.drawable.ic_down_arrow_black);
            imageSelectTypelesson.setImageResource(R.drawable.ic_down_arrow_black);
        }
    }

    private void setView(View view) {
        textSubject = view.findViewById(R.id.textSubject);
        textAudience = view.findViewById(R.id.textAudience);
        textEducator = view.findViewById(R.id.textEducator);
        textTypelesson = view.findViewById(R.id.textTypelesson);
        imageSelectSubject = view.findViewById(R.id.imageSelectSubject);
        imageSelectAudience = view.findViewById(R.id.imageSelectAudience);
        imageSelectEducator = view.findViewById(R.id.imageSelectEducator);
        imageSelectTypelesson = view.findViewById(R.id.imageSelectTypelesson);
        imageSubject = view.findViewById(R.id.imageSubject);
        imageAudience = view.findViewById(R.id.imageAudience);
        imageEducator = view.findViewById(R.id.imageEducator);
        imageTypelesson = view.findViewById(R.id.imageTypelesson);
        btnNext = view.findViewById(R.id.btnNext);
    }

    private void setListeners() {
        imageSelectSubject.setOnClickListener(this);
        imageSelectAudience.setOnClickListener(this);
        imageSelectEducator.setOnClickListener(this);
        imageSelectTypelesson.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imageSelectSubject) {
            presenter.onSubjectClick();
        }
        if (id == R.id.imageSelectAudience) {
            presenter.onAudienceClick();
        }
        if (id == R.id.imageSelectEducator) {
            presenter.onEducatorClick();
        }
        if (id == R.id.imageSelectTypelesson) {
            presenter.onTypelessonClick();
        }
        if (id == R.id.btnNext) {
            presenter.onNext(textSubject.getText().toString(), textAudience.getText().toString(),
                    textEducator.getText().toString(), textTypelesson.getText().toString());
        }
    }


    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}

