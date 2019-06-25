package myapp.schedule.misha.myapplication.module.schedule.edit;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragment;

public class EditScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<EditSchedulePageFragment> fragments = new ArrayList<>();

    public EditScheduleFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        int selectedWeek = Preferences.getInstance().getSelectedWeekSchedule();
        for (int day = 0; day < 6; day++) {
            fragments.add(EditSchedulePageFragment.newInstance(selectedWeek, day));
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @SuppressLint("ResourceType")
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void setWeek(int selectedWeek) {
        for (EditSchedulePageFragment fragment : fragments) {
            fragment.setWeek(selectedWeek);
        }
    }
}
