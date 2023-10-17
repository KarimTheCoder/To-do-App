package com.example.to_doapp.data.repository;


import android.app.Application;

import androidx.paging.DataSource;

import com.example.to_doapp.data.ListNamesCallback;
import com.example.to_doapp.data.dao.TaskDao;
import com.example.to_doapp.data.database.AppDatabase;
import com.example.to_doapp.data.entity.TaskEntity;

import java.util.List;

import kotlinx.coroutines.scheduling.Task;

public class TaskRepository {

    private final TaskDao dao;
    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.taskDao();
    }

    public void updateTaskName(TaskEntity task, String name){
        task.setTaskName(name);
        AppDatabase.executor.execute(()-> dao.update(task));

    }
    public void deleteTask(TaskEntity task){
        AppDatabase.executor.execute(()-> dao.delete(task));
    }

    public void addTask(TaskEntity task){
        AppDatabase.executor.execute(()-> dao.addTask(task));
    }
    public DataSource.Factory<Integer, TaskEntity> getItemsByListName(String listName){
        return dao.getItemsByListName(listName);
    }
    public DataSource.Factory<Integer, TaskEntity> getItemsByListId(int listId){
        return dao.getItemsByListId(listId);
    }

    public void deleteItem(int id, String listName){

        AppDatabase.executor.execute(()-> dao.deleteItemByIdAndListName(id,listName));
    }
    public int deleteList(String listName){
        return dao.deleteList(listName);
    }
    public void updateListItem(String listName, int id, String newTaskName){
        AppDatabase.executor.execute(()-> dao.updateListItem(listName, id, newTaskName));
    }
    public void getListNames(ListNamesCallback callback) {
        new Thread(() -> {
            List<String> listNames = dao.getUniqueListNames();
            callback.onListNamesLoaded(listNames);
        }).start();
    }

}





