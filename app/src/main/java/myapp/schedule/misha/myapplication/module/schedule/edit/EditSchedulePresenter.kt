package myapp.schedule.misha.myapplication.module.schedule.edit

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter
import myapp.schedule.misha.myapplication.data.preferences.Preferences

class EditSchedulePresenter : BaseMainPresenter<EditScheduleFragmentView>(), EditSchedulePresenterInterface {

    override fun init() {
        val currentDay = Preferences.getInstance().selectedPositionTabDays
        val currentWeek = Preferences.getInstance().selectedWeekSchedule
        view.selectCurrentDay(currentDay)
        view.selectCurrentWeek(currentWeek)
    }

    override fun onWeekSelected(position: Int) {
        view.selectWeek(position)
    }

    override fun onPageSwiped(position: Int) {
        Preferences.getInstance().selectedPositionTabDays = position
        view.swipePage(position)
    }

    override fun onPageSelected(position: Int) {
        Preferences.getInstance().selectedPositionTabDays = position
        view.selectPage(position)
    }
}
