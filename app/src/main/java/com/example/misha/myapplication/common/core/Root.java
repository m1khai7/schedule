package com.example.misha.myapplication.common.core;

import android.view.View;

import com.example.misha.myapplication.common.ErrorView;


public interface Root {

    void showProgressDialog();

    void hideProgressDialog();

    void hideProgressBar();

    void showProgressBar();

    void showErrorView();

    void hideErrorView();

    void showSnack(String message);

    void showSnack(int stringResId);

    void showSnack(String message, String button, View.OnClickListener callback);

    void setOnErrorClick(ErrorView.ErrorListener listener);
}
