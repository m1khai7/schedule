package myapp.schedule.misha.myapplication.module.editData.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface EditDataFragmentPageView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";


    void updateItemsAdapter(ArrayList<SimpleItem> listItems);

    void setupWidgets(EditDataModel editDataModel);

    void showEditDataDialog(ArrayList<? extends SimpleItem> items, int position);
}
