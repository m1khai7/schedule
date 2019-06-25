package myapp.schedule.misha.myapplication.common.core;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import myapp.schedule.misha.myapplication.activity.MainActivity;


public abstract class BaseDialog extends DialogFragment {

    public void showProgressDialog() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            BaseActivity root = getContext();
            root.showProgressDialog();
        }
    }

    @Override
    public void show(@NotNull FragmentManager manager, String tag) {
        try {
            Field mDismissed = DialogFragment.class.getDeclaredField("mDismissed");
            Field mShownByMe = DialogFragment.class.getDeclaredField("mShownByMe");
            mDismissed.setAccessible(true);
            mShownByMe.setAccessible(true);
            mDismissed.setBoolean(this, false);
            mShownByMe.setBoolean(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    public void hideProgressDialog() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            Root root = getContext();
            root.hideProgressDialog();
        }
    }

    public void showProgressBar() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            Root root = getContext();
            root.showProgressBar();
        }
    }

    public void hideProgressBar() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            Root root = getContext();
            root.hideProgressBar();
        }
    }

    public void showErrorView() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            Root root = getContext();
            root.showErrorView();
        }
    }

    public void hideErrorView() {
        if (getContext() != null && getActivity() instanceof MainActivity) {
            Root root = getContext();
            root.hideErrorView();
        }
    }

    public BaseActivity getContext() {
        return (BaseActivity) getActivity();
    }
}
