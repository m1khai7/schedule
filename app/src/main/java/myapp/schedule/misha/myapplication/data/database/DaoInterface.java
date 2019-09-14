package myapp.schedule.misha.myapplication.data.database;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.Subject;

public interface DaoInterface<T> {

    T getItemByID(long id);

    void insertItem(T item);

    void insertAll(ArrayList<T> items);

    void deleteAll();

    void updateItem(T item, long id);

    boolean deleteItemById(long id);
}
