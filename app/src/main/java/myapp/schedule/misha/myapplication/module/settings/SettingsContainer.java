package myapp.schedule.misha.myapplication.module.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.common.core.Container;
import myapp.schedule.misha.myapplication.module.settings.menu.MenuFragment;

public class SettingsContainer extends BaseMainFragment implements Container {

    private SettingsContainerPresenter presenter =  new SettingsContainerPresenter();

    public static SettingsContainer newInstance() {
        Bundle args = new Bundle();
        SettingsContainer fragment = new SettingsContainer();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(MenuFragment.newInstance());
    }


    @NonNull
    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }
}
