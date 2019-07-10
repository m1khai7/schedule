package myapp.schedule.misha.myapplication.module.container;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.DialogCopyFragment;

public class Container extends BaseMainFragment implements ContainerView {

    private  ContainerPresenter presenter;
    private final DialogCopyFragment dialogCopyFragment = DialogCopyFragment.newInstance();

    public static Container newInstance() {

        Bundle args = new Bundle();
        Container fragment = new Container();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContainerPresenter();
        replaceFragment(dialogCopyFragment);
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }


    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }

}

