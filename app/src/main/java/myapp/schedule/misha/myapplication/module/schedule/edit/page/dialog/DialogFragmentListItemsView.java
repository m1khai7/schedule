package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialog;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface DialogFragmentListItemsView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";

    void showAddDataDialog(ArrayList<? extends SimpleItem> items, int fragmentCode);
}
