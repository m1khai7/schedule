package myapp.schedule.misha.myapplication.common.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.ErrorView;


public abstract class BaseMainFragment extends BaseFragment implements BaseView {

    @Override
    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, true);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean animate) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().getBackStackEntryCount() >= 1 && animate) {
            transaction.setCustomAnimations(R.anim.tr_child_up, R.anim.tr_exit_left,
                    R.anim.tr_parent_back, R.anim.tr_child_back);
        }
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }



    public void clearBackStack() {
        if (getChildFragmentManager().getBackStackEntryCount() != 0 && isAdded()) {
            String name = getChildFragmentManager().getBackStackEntryAt(0).getName();
            getChildFragmentManager().popBackStackImmediate(name, 0);
        }
    }

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
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            ErrorView errorView = getContext().findViewById(R.id.view_error);
            errorView.setError(message);
        }
    }

    @Override
    public void showSnack(String message) {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showSnack(message);
        }
    }

    @Override
    public void showSnack(String message, String button, View.OnClickListener callback) {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showSnack(message, button, callback);
        }
    }

    @Override
    public void showSnack(@StringRes int message) {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showSnack(message);
        }
    }

    @Override
    public void showGlobalError(String message) {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            ErrorView errorView = getContext().findViewById(R.id.view_error);
            errorView.setError(message);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarEnabled(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().setRoot(getRoot());
        getPresenter().setView(this);
    }

    public Root getRoot() {
        return getContext();
    }

    public void popBackStackInclusive(String name) {
        getChildFragmentManager().popBackStackImmediate(name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStackInclusive(Class className) {
        popBackStackInclusive(className.getName());
    }

    public void popBackStack(Class className) {
        popBackStack(className.getName());
    }

    public void popBackStack(String name) {
        getChildFragmentManager().popBackStackImmediate(name, 0);
    }

    public void popBackStack() {
        getChildFragmentManager().popBackStackImmediate();
    }


    public void sendResultToTarget(Class target, int request, int result, Intent data) {
        sendResultToTarget(target, null, request, result, data);
    }


    private void sendResultToTarget(Class target, Fragment root, int request,
                                    int result, Intent data) {
        List<Fragment> activeFragments = getContext().getFragments(root == null
                ? getChildFragmentManager()
                : root.getChildFragmentManager());
        if (activeFragments == null) return;
        for (Fragment fragment : activeFragments) {
            if (fragment == null) continue;
            Class fragmentClass = fragment.getClass();
            if (fragmentClass.equals(target)) {
                fragment.onActivityResult(request, result, data);
                return;
            }
            sendResultToTarget(target, fragment, request, result, data);
        }
    }

    public boolean handleBackPressed(FragmentManager manager) {
        if (manager.getFragments() == null) return false;
        for (Fragment frag : manager.getFragments()) {
            if (frag == null) continue;
            if (frag.isVisible() && frag instanceof BaseMainFragment && ((BaseMainFragment) frag).onBackPressed()) {
                return true;
            }
        }
        return false;
    }

    protected boolean onBackPressed() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (handleBackPressed(childFragmentManager)) {
            return true;
        } else if (getUserVisibleHint() && childFragmentManager.getBackStackEntryCount() > 1) {
            childFragmentManager.popBackStack();
            return true;
        }
        return false;
    }


    public BaseMainFragment getParent() {
        return (BaseMainFragment) getParentFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //noinspection unchecked
        getPresenter().setRoot(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
