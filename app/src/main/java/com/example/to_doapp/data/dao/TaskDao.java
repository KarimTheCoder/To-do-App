package com.example.to_doapp.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.to_doapp.data.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void addTask(TaskEntity task);
    @Update
    void update(TaskEntity task);
    @Delete
    void delete(TaskEntity task);

    @Query("SELECT * FROM task_table WHERE listName = :listName")
    DataSource.Factory<Integer, TaskEntity> getItemsByListName(String listName);

    @Query("SELECT * FROM task_table WHERE listId = :listId")
    DataSource.Factory<Integer, TaskEntity> getItemsByListId(int listId);

    @Query("DELETE FROM task_table WHERE id = :taskId AND listName = :listName")
    int deleteItemByIdAndListName(int taskId, String listName);


    @Query("DELETE FROM task_table WHERE listName = :listName")
    int deleteList(String listName);

    @Query("UPDATE task_table SET taskName = :newTaskName WHERE listName = :listName AND id = :id")
    int updateListItem(String listName, int id, String newTaskName);

    @Query("SELECT DISTINCT listName FROM task_table")
    List<String> getUniqueListNames();



}
