package myapp.schedule.misha.myapplication.module.settings.licenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseDialog;
import myapp.schedule.misha.myapplication.entity.Licenses;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView;

//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.

public class DialogFragmentLicenses extends BaseDialog {

    public static DialogFragmentLicenses newInstance(Bundle args) {
        DialogFragmentLicenses fragment = new DialogFragmentLicenses();
        fragment.setArguments(args);
        return fragment;
    }


    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_rv_licenses, null);
        ArrayList<Licenses> licensesList = getArguments().getParcelableArrayList(EditSchedulePageFragmentView.ITEMS);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        RecyclerView rvItems = view.findViewById(R.id.rv_dialog);
        DialogFragmentLicensesAdapter dialogFragmenLicensesAdapter = new DialogFragmentLicensesAdapter(licensesList);
        rvItems.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvItems.setAdapter(dialogFragmenLicensesAdapter);
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(v -> dismiss());
        return builder.create();
    }
}
