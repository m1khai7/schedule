package myapp.schedule.misha.myapplication.module.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static myapp.schedule.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;

public class DialogFragmentSelectTheme extends DialogFragment {

    public static DialogFragmentSelectTheme newInstance() {
        return new DialogFragmentSelectTheme();
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] listItems = {"Темная", "Светлая"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.title_select_theme));
        int checkedItem = 0;
        String nameTheme = Preferences.getInstance().getSelectedTheme();
        if (nameTheme.equals(LIGHT_THEME)) {
            checkedItem = 1;
        }

        builder.setSingleChoiceItems(listItems, checkedItem, (dialog, which) -> {
            if (which == 0) {
                Preferences.getInstance().setSelectedTheme(DARK_THEME);
                getContext().setTheme(R.style.DarkTheme);
            }
            if (which == 1) {
                Preferences.getInstance().setSelectedTheme(LIGHT_THEME);
                getContext().setTheme(R.style.LightTheme);
            }
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        });

        builder.setPositiveButton("Отмена", (dialog, which) -> dismiss());
        return builder.create();
    }

}
