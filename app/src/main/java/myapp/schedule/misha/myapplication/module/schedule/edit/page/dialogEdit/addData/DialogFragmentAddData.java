package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.addData;

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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.util.DataUtil;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDIT_DATA;

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
        AlertDialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button button_cancel = view.findViewById(R.id.btn_cancel);
        button_cancel.setOnClickListener(v -> {
            DataUtil.showKeyboard(getContext());
            Intent intent = new Intent();
            getParentFragment().onActivityResult(editDataModel.getType(), Activity.RESULT_OK, intent);
            dismiss();
        });
        return d;
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

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
