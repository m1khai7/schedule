package myapp.schedule.misha.myapplication.module.editData.page;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface EditDataFragmentPageView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";


    void updateItemsAdapter(ArrayList<SimpleItem> listItems);

    void setupWidgets(EditDataModel editDataModel);

    void showEditDataDialog(ArrayList<? extends SimpleItem> items, int position);
}
