package com.example.misha.myapplication.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.misha.myapplication.R;

public class ErrorView extends LinearLayout implements View.OnClickListener {

    private ErrorListener errorListener;

    private TextView error;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_error, this);
        error = view.findViewById(R.id.tv_message);
        view.findViewById(R.id.btn_retry).setOnClickListener(this);
    }

    public void setError(String message) {
        error.setText(message);
    }

    public void setError(@StringRes int message) {
        error.setText(message);
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_retry) {
            if (errorListener != null) {
                setVisibility(GONE);
                errorListener.onReloadData();
            }
        }
    }

    public interface ErrorListener {

        void onReloadData();

    }
}
