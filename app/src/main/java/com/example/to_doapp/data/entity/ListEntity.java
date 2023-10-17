package com.example.to_doapp.data.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_table")
public class ListEntity {

    @PrimaryKey(autoGenerate = true)
    int listId;
    String listCategory;
    String listName;

    public String getListCategory() {
        return listCategory;
    }
    public void setListCategory(String listCategory) {
        this.listCategory = listCategory;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
