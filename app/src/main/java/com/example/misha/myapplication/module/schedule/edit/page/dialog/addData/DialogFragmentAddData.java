package com.example.misha.myapplication.module.schedule.edit.page.dialog.addData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseAlertDialog;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.entity.EditDataModel;

import org.jetbrains.annotations.NotNull;

import static com.example.misha.myapplication.Constants.FRAGMENT_EDIT_DATA;

public class DialogFragmentAddData extends BaseAlertDialog implements TextView.OnEditorActionListener {
    private DialogFragmentDataPresenter presenter;
    private EditText inputItem;

    public static DialogFragmentAddData newInstance(EditDataModel editDataModel) {
        Bundle args = new Bundle();
        args.putParcelable(FRAGMENT_EDIT_DATA, editDataModel);
        DialogFragmentAddData fragment = new DialogFragmentAddData();
        fragment.setArguments(args);
        return fragment;
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        EditDataModel editDataModel = getArguments().getParcelable(FRAGMENT_EDIT_DATA);
        presenter = new DialogFragmentDataPresenter(editDataModel);
        presenter.init();

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.dialog_add_data, null);
        TextView title_dialog = view.findViewById(R.id.dialog_textView);
        inputItem = view.findViewById(R.id.dialog_editText);
        inputItem.setOnEditorActionListener(this);

        title_dialog.setText(editDataModel.getTitle());
        inputItem.setHint(editDataModel.getHint());
        inputItem.setInputType(editDataModel.getInputType());
        inputItem.setFilters(new InputFilter[]{new InputFilter.LengthFilter(editDataModel.getMaxLenth())});

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> {
            Intent intent = new Intent();
            getParentFragment().onActivityResult(editDataModel.getType(), Activity.RESULT_OK, intent);
            dismiss();
        });
        return builder.create();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        EditDataModel editDataModel = getArguments().getParcelable(FRAGMENT_EDIT_DATA);
        if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
            inputItem.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            String itemName = inputItem.getText().toString();
            itemName = itemName.trim().replaceAll(" +", " ");
            if (TextUtils.isEmpty(itemName) || itemName.equals(" ")) {
                inputItem.setError(getText(editDataModel.getError()));
                return true;
            }
            presenter.insert(itemName, editDataModel.getType());
            inputItem.getText().clear();
            return true;
        } else {
            return false;
        }
    }


    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
