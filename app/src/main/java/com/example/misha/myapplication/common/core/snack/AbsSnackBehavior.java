package com.example.misha.myapplication.common.core.snack;

import android.view.View;

public abstract class AbsSnackBehavior implements SnackBehaviorInterface {

    private View root;

    public AbsSnackBehavior(View root) {
        this.root = root;
    }

    public View getRoot() {
        return root;
    }
}
