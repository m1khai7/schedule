package myapp.schedule.misha.myapplication.module.settings.lesson;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.common.core.Container;
import myapp.schedule.misha.myapplication.module.settings.lesson.fragment.CreateLessonFragment;

public class CreateLessonContainer extends BaseMainFragment implements Container {

    private CreateLessonContainerPresenter presenter =  new CreateLessonContainerPresenter();

    public static CreateLessonContainer newInstance() {
        return new CreateLessonContainer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(CreateLessonFragment.newInstance());
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }
}
