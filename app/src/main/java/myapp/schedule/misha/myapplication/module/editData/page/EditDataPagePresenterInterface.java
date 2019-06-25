package myapp.schedule.misha.myapplication.module.editData.page;

public interface EditDataPagePresenterInterface {

    void onClearClick(int position);

    void insert(String itemName, int type);

    String getNameAt(int position);
}
