package myapp.schedule.misha.myapplication.module.settings.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;

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
            presenter.showSubjects();
        }
        if (id == R.id.imageSelectAudience) {
            presenter.showAudiences();
        }
        if (id == R.id.imageSelectEducator) {
            presenter.showEducators();
        }
        if (id == R.id.imageSelectTypelesson) {
            presenter.showTypelessons();
        }
        if (id == R.id.btnNext) {
            presenter.onNext();
        }
    }


    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}

