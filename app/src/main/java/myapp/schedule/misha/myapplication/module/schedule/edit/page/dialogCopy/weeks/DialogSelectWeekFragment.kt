package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import myapp.schedule.misha.myapplication.Constants
import myapp.schedule.misha.myapplication.R
import myapp.schedule.misha.myapplication.common.core.BaseAlertDialog
import myapp.schedule.misha.myapplication.common.core.BasePresenter
import myapp.schedule.misha.myapplication.entity.Weeks

class DialogSelectWeekFragment : BaseAlertDialog(), DialogSelectWeekFragmentView {

    private var presenter: DialogSelectWeekFragmentPresenter? = null

    private var dialogFragmentListItemsAdapter: DialogSelectWeekFragmentAdapter? = null

    private var listWeeks: ArrayList<Weeks>? = ArrayList()

    private val checkOnFullSelect: Boolean?
        get() {
            var checkOnFullSelect = false
            for (week in listWeeks!!) {
                checkOnFullSelect = !week.isChecked
            }
            return checkOnFullSelect
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        presenter = DialogSelectWeekFragmentPresenter()
        dialogFragmentListItemsAdapter = DialogSelectWeekFragmentAdapter()
        val layoutInflater = LayoutInflater.from(context)
        listWeeks = arguments!!.getParcelableArrayList(Constants.LIST_ITEMS)
        val code = arguments!!.getInt(Constants.FRAGMENT)
        val view = layoutInflater.inflate(R.layout.dialog_rv_weeks, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val rvItems = view.findViewById<RecyclerView>(R.id.rv_dialog_list)
        rvItems.addItemDecoration(DividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL))
        rvItems.adapter = dialogFragmentListItemsAdapter
        updateAdapter()
        view.findViewById<View>(R.id.btn_ok).setOnClickListener {
            var countUnselectedWeek = 0
            for (week in listWeeks!!) {
                if (!week.isChecked) countUnselectedWeek += 1
            }
            if (listWeeks!!.size == countUnselectedWeek) {
                showError(R.string.error_selected_week)
            } else {
                listWeeks = dialogFragmentListItemsAdapter!!.listWeeks
                val intent = Intent()
                intent.putExtra(Constants.LIST_ITEMS, listWeeks)
                if (code == DialogSelectWeekFragmentView.LIST_ITEMS)
                    parentFragment!!.onActivityResult(DialogSelectWeekFragmentView.LIST_ITEMS, Activity.RESULT_OK, intent)
                if (code == DialogSelectWeekFragmentView.CLEAR)
                    parentFragment!!.onActivityResult(DialogSelectWeekFragmentView.CLEAR, Activity.RESULT_OK, intent)
                if (code == DialogSelectWeekFragmentView.COPY)
                    parentFragment!!.onActivityResult(DialogSelectWeekFragmentView.COPY, Activity.RESULT_OK, intent)
                dismiss()
            }
        }
        view.findViewById<View>(R.id.btn_cancel).setOnClickListener { v -> dismiss() }
        view.findViewById<View>(R.id.btn_select_all).setOnClickListener { v -> presenter!!.onSelectAllClicked() }
        return builder.create()
    }

    override fun selectAll() {
        for (week in listWeeks!!) {
            week.setCheck(checkOnFullSelect)
        }
        dialogFragmentListItemsAdapter!!.listWeeks = listWeeks
    }

    override fun updateAdapter() {
        dialogFragmentListItemsAdapter!!.listWeeks = listWeeks
    }

    override fun getPresenter(): BasePresenter<*, *> {
        return presenter!!
    }

    companion object {
        fun newInstance(listWeeks: ArrayList<Weeks>, code: Int): DialogSelectWeekFragment {
            val args = Bundle()
            args.putParcelableArrayList(Constants.LIST_ITEMS, listWeeks)
            args.putInt(Constants.FRAGMENT, code)
            val fragment = DialogSelectWeekFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
