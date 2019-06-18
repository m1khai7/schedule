package com.example.misha.myapplication.common.core;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.ScheduleApp;
import com.example.misha.myapplication.common.ErrorView;
import com.example.misha.myapplication.common.core.snack.SnackBehavior;
import com.example.misha.myapplication.common.core.snack.SnackBehaviorInterface;


public abstract class BaseMainDialog extends BaseDialog implements BaseDialogView {

    private Toolbar toolbar;

    private SnackBehaviorInterface snack;

    @Override
    public void showError(@StringRes int message) {
        ScheduleApp.showToast(message);
    }

    @Override
    public void showError(String message) {
        ScheduleApp.showToast(message);
    }

    @Override
    public void showGlobalError(@StringRes int message) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ErrorView errorView = getContext().findViewById(R.id.view_error);
                errorView.setError(message);
            }
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void showGlobalError(String message) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ErrorView errorView = getContext().findViewById(R.id.view_error);
                errorView.setError(message);
            }
        }
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    public abstract String getTitle();

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        snack = new SnackBehavior(getView());
        toolbar = getView().findViewById(R.id.toolbar_actionbar);
        toolbar.setNavigationOnClickListener(view1 -> closeDialog());
        toolbar.setTitle(getTitle());
        BaseActivity baseActivity = (BaseActivity) getActivity();
        //noinspection unchecked
        getPresenter().setRoot(baseActivity);
        //noinspection unchecked
        getPresenter().setView(this);
    }

    @Override
    public void showSnack(String message) {
        snack.showSnack(message);
    }


    @Override
    public void showSnack(@StringRes int message) {
        snack.showSnack(message);
    }

    @Override
    public void showSnack(String message, String button, View.OnClickListener callback) {
        snack.showSnack(message, button, callback);
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getContext(), R.style.DarkTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, getClass().getSimpleName());
    }

    public void popBackStackInclusive(String name) {
        getChildFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStackInclusive(Class className) {
        popBackStackInclusive(className.getSimpleName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().setRoot(null);
        getPresenter().getCompositeDisposable().dispose();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @NonNull
    protected abstract BasePresenter getPresenter();
}
