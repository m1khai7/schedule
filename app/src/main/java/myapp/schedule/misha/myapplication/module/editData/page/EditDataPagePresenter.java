package myapp.schedule.misha.myapplication.module.editData.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class EditDataPagePresenter extends BaseMainPresenter<EditDataFragmentPageView> implements EditDataPagePresenterInterface {

    private final EditDataModel editDataModel;

    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    private AbsDao absDao;


    public EditDataPagePresenter(EditDataModel editDataModel) {
        this.editDataModel = editDataModel;
        absDao = editDataModel.getDao();
    }

    public void onClearClick(int position) {
        getView().showEditDataDialog(listItems, position);
    }

    @Override
    public void init() {
        listItems = absDao.getAllData();
        getView().setupWidgets(editDataModel);
        getView().updateItemsAdapter(listItems);
    }

    public ArrayList<SimpleItem> getItemList() {
        listItems = absDao.getAllData();
        return listItems;
    }

    @Override
    public void insert(String itemName, int type) {
        SimpleItem item = null;
        if (type == FRAGMENT_SUBJECTS) {
            item = new Subject();
        }
        if (type == FRAGMENT_AUDIENCES) {
            item = new Audience();
        }
        if (type == FRAGMENT_EDUCATORS) {
            item = new Educator();
        }
        if (type == FRAGMENT_TYPELESSONS) {
            item = new Typelesson();
        }
        item.setName(itemName);
        absDao.insertItem(item);
        listItems = absDao.getAllData();
        getView().updateItemsAdapter(listItems);
    }

    @Override
    public String getNameAt(int position) {
        return listItems.get(position).getName();
    }

}
