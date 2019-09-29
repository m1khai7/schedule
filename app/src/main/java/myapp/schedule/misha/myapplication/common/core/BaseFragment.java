package myapp.schedule.misha.myapplication.common.core;

import android.graphics.PorterDuff;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.ErrorView;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;

public abstract class BaseFragment extends Fragment {

    private boolean collapsible = false;
    private boolean toolbarEnabled = true;

    public void setToolbarEnabled(boolean state) {
        this.toolbarEnabled = state;
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean expand = true;
        if (collapsible) {
            getContext().enableCollapsing(expand);
        } else {
            getContext().disableCollapsing();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getContext().setToolbarVisibility(toolbarEnabled);
        setOnErrorClick(getOnErrorClick());
    }

    public ErrorView.ErrorListener getOnErrorClick() {
        if (this instanceof ErrorView.ErrorListener) {
            return (ErrorView.ErrorListener) this;
        } else {
            return null;
        }
    }

    public void setOnErrorClick(ErrorView.ErrorListener errorListener) {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.setOnErrorClick(errorListener);
        }
    }

    public void setDrawerEnabled(boolean drawerEnabled) {
        boolean drawerEnabled1 = drawerEnabled;
    }

    public void setCollapsible(boolean state) {
        this.collapsible = state;
    }

    public void hideToolbarIcon() {
        getContext().getToolbar().setNavigationIcon(null);
    }

    public void showIcon() {
        getContext().getToolbar().setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        String nameTheme = Preferences.getInstance().getSelectedTheme();
        getContext().getToolbar().getNavigationIcon().setColorFilter(ScheduleApp.getClr(nameTheme.equals(DARK_THEME) ?
                R.color.white : R.color.black20), PorterDuff.Mode.SRC_ATOP);
    }

    public void showProgressDialog() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showProgressDialog();
        }
    }

    public void hideProgressDialog() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.hideProgressDialog();
        }
    }

    public void showProgressBar() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showProgressBar();
        }
    }

    public void hideProgressBar() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.hideProgressBar();
        }
    }

    public void showErrorView() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.showErrorView();
        }
    }

    public void hideErrorView() {
        if (this.getActivity() != null && getActivity() instanceof BaseActivity) {
            Root baseRouter = (Root) getActivity();
            baseRouter.hideErrorView();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        removeErrorClick();
    }

    public void removeErrorClick() {
        ErrorView errorView = getContext().findViewById(R.id.view_error);
        errorView.setErrorListener(null);
    }

    @NonNull
    public BaseActivity getContext() {
        return (BaseActivity) getActivity();
    }
}
