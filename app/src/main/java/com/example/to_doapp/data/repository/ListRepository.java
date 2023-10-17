package com.example.to_doapp.data.repository;


import android.app.Application;

import androidx.paging.DataSource;

import com.example.to_doapp.data.dao.ListDao;
import com.example.to_doapp.data.database.AppDatabase;
import com.example.to_doapp.data.entity.ListEntity;

public class ListRepository {

    private ListDao listDao;



    public ListRepository(Application application){

        AppDatabase db = AppDatabase.getDatabase(application);
        listDao = db.listDao();
    }

    public void addList(ListEntity list){
        AppDatabase.executor.execute(() -> listDao.insertList(list));
    }
    public void deleteList(int listId){
        AppDatabase.executor.execute(() -> listDao.deleteListById(listId));
    }
    public void updateList(ListEntity list){
        AppDatabase.executor.execute(() -> listDao.updateList(list));
    }
    public DataSource.Factory<Integer, ListEntity>  getAllLists(){

        return listDao.getAllLists();
    }

    public DataSource.Factory<Integer, ListEntity> getListByCategory(String category){

        return listDao.getListByCategory(category);
    }




}
