package myapp.schedule.misha.myapplication.module.schedule.edit.page

import android.app.Activity
import android.app.AlertDialog
import myapp.schedule.misha.myapplication.Constants.*
import myapp.schedule.misha.myapplication.R
import myapp.schedule.misha.myapplication.common.core.BaseActivity
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter
import myapp.schedule.misha.myapplication.data.database.dao.*
import myapp.schedule.misha.myapplication.data.preferences.Preferences
import myapp.schedule.misha.myapplication.entity.Lesson
import myapp.schedule.misha.myapplication.entity.Weeks
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragmentView

class EditSchedulePagePresenter(private val context: BaseActivity, private var positionWeek: Int, private var positionDay: Int) :
        BaseMainPresenter<EditSchedulePageFragmentView>(), EditSchedulePagePresenterInterface {

    private var lessonList: ArrayList<Lesson> = ArrayList()
    private var lessonListWeekCurrent: ArrayList<Lesson> = ArrayList()

    override fun init() {
        updateList(positionWeek, Preferences.getInstance().selectedPositionTabDays + 1)
    }

    override fun onSubjectClick(position: Int) {
        val subjectList = SubjectDao.getInstance().allData
        view.showEditDialog(subjectList, position, FRAGMENT_SUBJECTS)
    }

    override fun onAudienceClick(position: Int) {
        val audienceList = AudienceDao.getInstance().allData
        view.showEditDialog(audienceList, position, FRAGMENT_AUDIENCES)
    }

    override fun onEducatorClick(position: Int) {
        val educatorList = EducatorDao.getInstance().allData
        view.showEditDialog(educatorList, position, FRAGMENT_EDUCATORS)
    }

    override fun onTypelessonClick(position: Int) {
        val typelessonList = TypelessonDao.getInstance().allData
        view.showEditDialog(typelessonList, position, FRAGMENT_TYPELESSONS)
    }

    override fun onClickMainFab() {
        view.animateFAB()
    }

    override fun setWeek(position: Int) {
        this.positionWeek = position
        updateList(position, positionDay)
    }

    override fun lessonList(): ArrayList<Lesson> {
        return lessonList
    }

    override fun onCopyUpClick(position: Int) {
        val lesson = lessonList[position]
        lessonList[position - 1].run {
            id_subject = lesson.id_subject
            id_audience = lesson.id_audience
            id_typelesson = lesson.id_typelesson
            id_educator = lesson.id_educator
            view.updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[position - 1])
        }
    }

    override fun onCopyDownClick(position: Int) {
        val lesson = lessonList[position]
        lessonList[position + 1].run {
            id_subject = lesson.id_subject
            id_audience = lesson.id_audience
            id_typelesson = lesson.id_typelesson
            id_educator = lesson.id_educator
            view.updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[position + 1])
        }
    }

    override fun onClearLessonClick(position: Int) {
        lessonList[position].clear()
        view.updateView(lessonList)
        LessonDao.getInstance().updateItemByID(lessonList[position])
    }

    override fun onCopyLessonOtherDay(position: Int) {
        view.showCopyLesson(lessonList[position])
    }

    override fun onClearDayClick() {
        for (i in 0..5) {
            lessonList[i].clear()
            LessonDao.getInstance().updateItemByID(lessonList[i])
        }
        view.updateView(lessonList)
    }

    fun onClickCopyWeek() {
        view.openWeekDialog(DialogSelectWeekFragmentView.COPY)
    }

    override fun onClickClearWeek() {
        view.openWeekDialog(DialogSelectWeekFragmentView.CLEAR)
    }

    override fun onCreateDialogCopyWeek(listWeeks: ArrayList<Weeks>) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false).setPositiveButton(R.string.ack) { _, _ ->
            listWeeks.forEach {
                if (it.isChecked == true) {
                    lessonListWeekCurrent = LessonDao.getInstance().getLessonByWeek(positionWeek + 1)
                    lessonList = LessonDao.getInstance().getLessonByWeek(it.number)
                    for (idWeek in 0..35) {
                        lessonListWeekCurrent[idWeek].run {
                            lessonList[idWeek].setData(id_subject, id_audience, id_educator, id_typelesson)
                        }
                        LessonDao.getInstance().updateItemByID(lessonList[idWeek])
                    }
                }
            }
        }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }.setTitle(R.string.copy_week)
        builder.create().show()
    }

    override fun onCreateDialogClearWeek(listWeeks: ArrayList<Weeks>) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false).setPositiveButton(R.string.ack) { _, _ ->
            listWeeks.forEach {
                if (it.isChecked == true) {
                    lessonList = LessonDao.getInstance().getLessonByWeek(it.number)
                    for (idWeek in 0..35) {
                        lessonList[idWeek].clear()
                        LessonDao.getInstance().updateItemByID(lessonList[idWeek])
                        positionWeek = it.number
                    }
                }
            }
            context.sendResultToTarget(EditSchedulePageFragment::class.java, DialogSelectWeekFragmentView.ACK_CLEAR, Activity.RESULT_OK, null)
        }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }.setTitle(R.string.delete_week)
        return builder.create().show()
    }

    private fun updateList(positionWeek: Int, day: Int) {
        lessonList = LessonDao.getInstance().getLessonByWeekAndDay(positionWeek, day)
        view.updateView(lessonList)
    }
}
