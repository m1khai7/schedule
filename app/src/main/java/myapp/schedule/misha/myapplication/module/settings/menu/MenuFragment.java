package myapp.schedule.misha.myapplication.module.settings.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.module.settings.DialogFragmentSelectTheme;
import myapp.schedule.misha.myapplication.module.settings.lesson.CreateLessonFragment;
import myapp.schedule.misha.myapplication.module.settings.transfer.TransferFragment;
import myapp.schedule.misha.myapplication.util.DataUtil;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;


public class MenuFragment extends BaseMainFragment implements MenuFragmentView, View.OnClickListener {

    private MenuPresenter presenter;

    private TextView createLesson;
    private TextView dataStartSemester;
    private TextView changeTheme;
    private TextView transferData;
    private TextView developerInfo;


    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().setCurrentTitle(R.string.settings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MenuPresenter(getContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        DataUtil.hintKeyboard(getContext());
        setViews(view);
        setListeners();
        setThemeColorViews();
        return view;
    }

    private void setViews(View view) {
        createLesson = view.findViewById(R.id.createLesson);
        dataStartSemester = view.findViewById(R.id.dateStartSemester);
        changeTheme = view.findViewById(R.id.changeTheme);
        transferData = view.findViewById(R.id.transferData);
        developerInfo = view.findViewById(R.id.developerInfo);
    }

    private void setListeners() {
        createLesson.setOnClickListener(this);
        dataStartSemester.setOnClickListener(this);
        changeTheme.setOnClickListener(this);
        transferData.setOnClickListener(this);
        developerInfo.setOnClickListener(this);
    }

    private void setThemeColorViews() {
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            createLesson.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_createlesson_white, 0, R.drawable.ic_arrow_white, 0);
            dataStartSemester.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_palette_white, 0, R.drawable.ic_arrow_white, 0);
            changeTheme.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_send_white, 0, R.drawable.ic_arrow_white, 0);
            transferData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white, 0, R.drawable.ic_arrow_white, 0);
            developerInfo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info_white, 0, R.drawable.ic_arrow_white, 0);
        } else {
            createLesson.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_createlesson_black, 0, R.drawable.ic_arrow_black, 0);
            dataStartSemester.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_palette_black, 0, R.drawable.ic_arrow_black, 0);
            changeTheme.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_send_black, 0, R.drawable.ic_arrow_black, 0);
            transferData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black, 0, R.drawable.ic_arrow_black, 0);
            developerInfo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info_black, 0, R.drawable.ic_arrow_black, 0);
        }
    }

    public Dialog showDialogDevInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.
                setPositiveButton((R.string.string_vk_developer), (dialog, id) -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.vk_developer)));
                    startActivity(browserIntent);
                }).
                setNeutralButton(R.string.cancel, (dialog, id) -> dialog.cancel()).
                setNegativeButton((R.string.string_email_developer), (dialog, id) -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.email_developer)));
                    startActivity(browserIntent);
                }).
                setTitle(R.string.feedback_developer);
        return builder.create();
    }

    public void showDialogSelectTheme() {
        DialogFragmentSelectTheme.newInstance().show(getChildFragmentManager(), DialogFragmentSelectTheme.class.getSimpleName());
    }

    @Override
    public void showFragmentCreateLesson(){
        getParent().replaceFragment(CreateLessonFragment.newInstance());
    }

    public void openFragmentTransferData() {
        getParent().replaceFragment(TransferFragment.newInstance());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.createLesson) {
            presenter.onCreateLesson();
        }
        if (id == R.id.dateStartSemester) {
            presenter.onSelectDateStartSemester();
        }
        if (id == R.id.changeTheme) {
            presenter.OnSelectTheme();
        }
        if (id == R.id.transferData) {
            presenter.OnTransferData();
        }
        if (id == R.id.developerInfo) {
            presenter.OnShowDevInfo();
        }
    }


    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }


}
