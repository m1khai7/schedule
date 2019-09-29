package myapp.schedule.misha.myapplication.module.schedule.edit.page

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import myapp.schedule.misha.myapplication.Constants.*
import myapp.schedule.misha.myapplication.R
import myapp.schedule.misha.myapplication.activity.ActivityCopyLesson
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment
import myapp.schedule.misha.myapplication.common.core.BasePresenter
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao
import myapp.schedule.misha.myapplication.data.preferences.Preferences
import myapp.schedule.misha.myapplication.entity.*
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragment
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks.DialogSelectWeekFragmentView
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.DialogEditFragmentListItems
import java.util.*

class EditSchedulePageFragment : BaseMainFragment(), EditSchedulePageFragmentView, View.OnClickListener {

    private var rvadapter: EditScheduleFragmentPagerAdapter? = null
    private var mainFab: FloatingActionButton? = null
    private var evenWeekFab: FloatingActionButton? = null
    private var unevenWeekFab: FloatingActionButton? = null
    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var rotateForward: Animation? = null
    private var rotateBackward: Animation? = null
    private var presenter: EditSchedulePagePresenter? = null

    override fun onResume() {
        super.onResume()
        hideToolbarIcon()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val positionWeek = arguments!!.getInt(SELECTED_WEEK)
        val day = arguments!!.getInt(DAY)
        presenter = EditSchedulePagePresenter(context, positionWeek, day)
        rvadapter = EditScheduleFragmentPagerAdapter(presenter, context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.item_edit_schedule_recycler, container, false)
        mainFab = activity!!.findViewById(R.id.main_fab)
        evenWeekFab = activity!!.findViewById(R.id.copyWeek)
        unevenWeekFab = activity!!.findViewById(R.id.clearDay)
        mainFab!!.setOnClickListener(this)
        evenWeekFab!!.setOnClickListener(this)
        unevenWeekFab!!.setOnClickListener(this)
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward)
        val rvLessons = view.findViewById<RecyclerView>(R.id.rv_lessons_edit)
        rvLessons.adapter = rvadapter
        rvLessons.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && mainFab!!.visibility == View.VISIBLE) {
                    if (Preferences.getInstance().fabOpen) {
                        mainFab!!.hide()
                        mainFab!!.startAnimation(rotateBackward)
                        mainFab!!.isClickable = false
                        evenWeekFab!!.startAnimation(fabClose)
                        unevenWeekFab!!.startAnimation(fabClose)
                        evenWeekFab!!.isClickable = false
                        unevenWeekFab!!.isClickable = false
                        Preferences.getInstance().fabOpen = false
                    } else {
                        mainFab!!.hide()
                        mainFab!!.isClickable = false
                        Preferences.getInstance().fabOpen = false
                    }
                } else if (dy < 0 && mainFab!!.visibility != View.VISIBLE) {
                    mainFab!!.show()
                    mainFab!!.isClickable = true
                }
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Preferences.getInstance().fabOpen = false
        presenter!!.init()
    }

    override fun getPresenter(): BasePresenter<*, *> {
        return presenter!!
    }

    override fun updateView(listLessons: ArrayList<Lesson>) {
        rvadapter?.setLessonList(listLessons)
    }

    override fun setWeek(position: Int) {
        presenter!!.setWeek(position + 1)
    }

    override fun showEditDialog(listItems: ArrayList<out SimpleItem>, pos: Int, fragmentCode: Int) {
        val args = Bundle()
        args.putParcelableArrayList(EditSchedulePageFragmentView.ITEMS, listItems)
        args.putInt(EditSchedulePageFragmentView.POSITION, pos)
        args.putInt(EditSchedulePageFragmentView.FRAGMENT_CODE, fragmentCode)
        val dialogFragment = DialogEditFragmentListItems.newInstance(args)
        dialogFragment.show(childFragmentManager, DialogEditFragmentListItems::class.java.simpleName)
    }

    override fun showCopyLesson(currentLesson: Lesson) {
        val intent = Intent(activity, ActivityCopyLesson::class.java)
        intent.putExtra(EditSchedulePageFragmentView.CURRENT_LESSON, currentLesson)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultOk: Int, data: Intent?) {
        if (requestCode == FRAGMENT_SUBJECTS) {
            val lessonList = presenter!!.lessonList()
            val lessonPosition = data!!.getIntExtra(EditSchedulePageFragmentView.POSITION, 0)
            val subject = data.getParcelableExtra<Subject>(LIST_ITEMS)
            lessonList[lessonPosition].id_subject = subject!!.id
            updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[lessonPosition])
        }
        if (requestCode == FRAGMENT_TYPELESSONS) {
            val lessonList = presenter!!.lessonList()
            val lessonPosition = data!!.getIntExtra(EditSchedulePageFragmentView.POSITION, 0)
            val typelesson = data.getParcelableExtra<Typelesson>(LIST_ITEMS)
            lessonList[lessonPosition].id_typelesson = typelesson!!.id
            updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[lessonPosition])
        }
        if (requestCode == FRAGMENT_AUDIENCES) {
            val lessonList = presenter!!.lessonList()
            val lessonPosition = data!!.getIntExtra(EditSchedulePageFragmentView.POSITION, 0)
            val audience = data.getParcelableExtra<Audience>(LIST_ITEMS)
            lessonList[lessonPosition].id_audience = audience!!.id
            updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[lessonPosition])
        }
        if (requestCode == FRAGMENT_EDUCATORS) {
            val lessonList = presenter!!.lessonList()
            val lessonPosition = data!!.getIntExtra(EditSchedulePageFragmentView.POSITION, 0)
            val educator = data.getParcelableExtra<Educator>(LIST_ITEMS)
            lessonList[lessonPosition].setEducatorEdit(educator!!.id)
            updateView(lessonList)
            LessonDao.getInstance().updateItemByID(lessonList[lessonPosition])
        }
        if (requestCode == DialogSelectWeekFragmentView.CLEAR) {
            val listWeeks = data!!.getParcelableArrayListExtra<Weeks>(LIST_ITEMS)
            for (i in listWeeks!!.indices) {
                val week = listWeeks[i]
                week.number = i + 1
            }
            presenter!!.onCreateDialogClearWeek(listWeeks)
        }
        if (requestCode == DialogSelectWeekFragmentView.ACK_CLEAR) {
            presenter!!.init()
        }
        if (requestCode == DialogSelectWeekFragmentView.COPY) {
            val listWeeks = data!!.getParcelableArrayListExtra<Weeks>(LIST_ITEMS)
            for (i in listWeeks!!.indices) {
                val week = listWeeks[i]
                week.number = i + 1
            }
            presenter!!.onCreateDialogCopyWeek(listWeeks)
        }
    }

    override fun animateFAB() {
        if (Preferences.getInstance().fabOpen) {
            mainFab!!.startAnimation(rotateBackward)
            evenWeekFab!!.startAnimation(fabClose)
            unevenWeekFab!!.startAnimation(fabClose)
            evenWeekFab!!.isClickable = false
            unevenWeekFab!!.isClickable = false
            Preferences.getInstance().fabOpen = false
        } else {
            mainFab!!.startAnimation(rotateForward)
            evenWeekFab!!.startAnimation(fabOpen)
            unevenWeekFab!!.startAnimation(fabOpen)
            evenWeekFab!!.isClickable = true
            unevenWeekFab!!.isClickable = true
            Preferences.getInstance().fabOpen = true
        }
    }

    override fun openWeekDialog(code: Int) {
        val listWeeks = Weeks().newListWeeks
        val dialogFragment = DialogSelectWeekFragment.newInstance(listWeeks, code)
        dialogFragment.show(childFragmentManager, DialogSelectWeekFragment::class.java.simpleName)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_fab -> presenter!!.onClickMainFab()
            R.id.copyWeek -> presenter!!.onClickCopyWeek()
            R.id.clearDay -> presenter!!.onClickClearWeek()
        }
    }

    companion object {
        fun newInstance(posWeek: Int, posDay: Int): EditSchedulePageFragment {
            val args = Bundle()
            args.putInt(SELECTED_WEEK, posWeek)
            args.putInt(DAY, posDay + 1)
            val fragment = EditSchedulePageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
