package com.example.misha.myapplication.module.editData;

import android.text.InputType;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.entity.EditDataModel;
import com.example.misha.myapplication.module.editData.page.EditDataFragmentPage;

import org.jetbrains.annotations.NotNull;

import static com.example.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static com.example.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static com.example.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static com.example.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;


public class EditDataFragmentAdapter extends FragmentStatePagerAdapter {


    public EditDataFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = EditDataFragmentPage.newInstance(new EditDataModel(R.string.error_subject, R.string.hint_subject, R.string.delete_subject, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 60, FRAGMENT_SUBJECTS, 0, ""));
        } else if (position == 1) {
            fragment = EditDataFragmentPage.newInstance(new EditDataModel(R.string.error_audience, R.string.hint_audience, R.string.delete_audience, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 14, FRAGMENT_AUDIENCES, 0, ""));
        } else if (position == 2) {
            fragment = EditDataFragmentPage.newInstance(new EditDataModel(R.string.error_educator, R.string.hint_educator, R.string.delete_educator, InputType.TYPE_TEXT_FLAG_CAP_WORDS, 60, FRAGMENT_EDUCATORS, 0, ""));
        } else if (position == 3) {
            fragment = EditDataFragmentPage.newInstance(new EditDataModel(R.string.error_typelesson, R.string.hint_typelesson, R.string.delete_typelesson, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, 20, FRAGMENT_TYPELESSONS, 0, ""));
        }
        return fragment;
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Предмет";
        } else if (position == 1) {
            title = "Аудитор";
        } else if (position == 2) {
            title = "Преп-ль";
        } else if (position == 3) {
            title = "Тип/зан";
        }
        return title;
    }

}