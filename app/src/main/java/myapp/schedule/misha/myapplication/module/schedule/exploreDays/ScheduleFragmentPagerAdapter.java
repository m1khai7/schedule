package myapp.schedule.misha.myapplication.module.schedule.exploreDays;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.module.schedule.exploreDays.page.SchedulePageFragment;

public class ScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<SchedulePageFragment> fragments = new ArrayList<>();

    public ScheduleFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        int selectedWeek = Preferences.getInstance().getSelectedWeekSchedule();
        for (int day = 0; day < 6; day++) {
            fragments.add(SchedulePageFragment.newInstance(selectedWeek, day));
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
        for (SchedulePageFragment fragment : fragments) {
            fragment.setWeek(selectedWeek);
        }
    }
}
