package myapp.schedule.misha.myapplication.module.schedule.edit.page

import java.util.ArrayList

import myapp.schedule.misha.myapplication.common.core.BaseView
import myapp.schedule.misha.myapplication.entity.Lesson
import myapp.schedule.misha.myapplication.entity.SimpleItem

interface EditSchedulePageFragmentView : BaseView {

    fun updateView(listLessons: ArrayList<Lesson>)

    fun showEditDialog(listItems: ArrayList<out SimpleItem>, pos: Int, fragmentCode: Int)

    fun showCopyLesson(currentLesson: Lesson)

    fun setWeek(position: Int)

    fun animateFAB()

    fun openWeekDialog(code: Int)

    companion object {
        const val CURRENT_LESSON = "CURRENT_LESSON"
        const val ITEMS = "ITEMS"
        const val FRAGMENT_CODE = "FRAGMENT_CODE"
        const val POSITION = "POSITION"
    }
}
