package com.example.misha.myapplication.common.core.snack;

import android.view.View;

public interface SnackBehaviorInterface {

    void showSnack(String message);

    void showSnack(String message, String button, View.OnClickListener callback);

    void showSnack(int stringResId);
}
