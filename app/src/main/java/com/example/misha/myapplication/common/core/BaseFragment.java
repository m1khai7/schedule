package com.example.misha.myapplication.common.core;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.ErrorView;

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
