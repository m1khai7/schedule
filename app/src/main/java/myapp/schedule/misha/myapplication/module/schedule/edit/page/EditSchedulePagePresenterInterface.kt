package myapp.schedule.misha.myapplication.module.schedule.edit.page

import android.app.Dialog
import java.util.ArrayList

import myapp.schedule.misha.myapplication.entity.Lesson
import myapp.schedule.misha.myapplication.entity.Weeks

interface EditSchedulePagePresenterInterface {

    fun lessonList(): ArrayList<Lesson>

    fun onSubjectClick(position: Int)

    fun onAudienceClick(position: Int)

    fun onEducatorClick(position: Int)

    fun onTypelessonClick(position: Int)

    fun onCopyUpClick(position: Int)

    fun onCopyDownClick(position: Int)

    fun onClearLessonClick(position: Int)

    fun onCopyLessonOtherDay(position: Int)

    fun onClearDayClick()

    fun setWeek(position: Int)

    fun onClickMainFab()

    fun onClickClearWeek()

    fun onCreateDialogCopyWeek(listWeeks: ArrayList<Weeks>)

    fun onCreateDialogClearWeek(listWeeks: ArrayList<Weeks>)
}
