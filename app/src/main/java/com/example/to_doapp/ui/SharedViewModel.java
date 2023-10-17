package com.example.to_doapp.ui;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.to_doapp.data.entity.ListEntity;
import com.example.to_doapp.data.entity.TaskEntity;
import com.example.to_doapp.data.repository.ListRepository;
import com.example.to_doapp.data.repository.TaskRepository;

public  class SharedViewModel extends AndroidViewModel {


    private int selectedListId;
    private final ListRepository listRepository;
    private final TaskRepository taskRepository;

    public SharedViewModel(@NonNull Application application) {
        super(application);

        listRepository = new ListRepository(application);
        taskRepository = new TaskRepository(application);


    }
    public int getSelectedListId() {
        return selectedListId;
    }

    //Task repository methods
    public LiveData<PagedList<TaskEntity>> getItemsByListId(int listId){

        return  new LivePagedListBuilder<>(
                taskRepository.getItemsByListId(listId), /* page size */ 50).build();

    }
    public void addTask(TaskEntity task){
        taskRepository.addTask(task);
    }
    public void updateTaskName(TaskEntity task, String newTaskName){
        taskRepository.updateTaskName(task,newTaskName);
    }
    public void deleteTask(TaskEntity task){
        taskRepository.deleteTask(task);
    }


    //List repository methods
    public void setSelectedListId(int selectedListId) {
        this.selectedListId = selectedListId;
    }
    public void addList(ListEntity list){
        listRepository.addList(list);
    }
    public void deleteList(int listId){
        listRepository.deleteList(listId);
    }
    public LiveData<PagedList<ListEntity>> getAllLists(){

        return new LivePagedListBuilder<>(
                listRepository.getAllLists(), /* page size */ 50).build();
    }

    public LiveData<PagedList<ListEntity>> getListByCategory(String category){


        return new LivePagedListBuilder<>(
                listRepository.getListByCategory(category), /* page size */ 50).build();

    }

    public void updateList(ListEntity list){
        listRepository.updateList(list);
    }


}
