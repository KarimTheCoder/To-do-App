package com.example.to_doapp.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.to_doapp.data.entity.ListEntity;

@Dao
public interface ListDao {

    @Insert
    void insertList(ListEntity list);
    @Update
    void updateList(ListEntity list);
    @Query("DELETE FROM list_table WHERE listId = :listId")
    void deleteListById(int listId);
    @Query("SELECT * FROM list_table")
    DataSource.Factory<Integer, ListEntity> getAllLists();
    @Query("SELECT * FROM list_table WHERE listId = :id")
    ListEntity getListById(int id);
    @Query("SELECT * FROM list_table WHERE listCategory = :category")
    DataSource.Factory<Integer, ListEntity> getListByCategory(String category);

}
