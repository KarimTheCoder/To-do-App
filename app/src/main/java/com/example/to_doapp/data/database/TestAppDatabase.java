package com.example.to_doapp.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.to_doapp.data.dao.ListDao;
import com.example.to_doapp.data.dao.TaskDao;
import com.example.to_doapp.data.entity.ListEntity;
import com.example.to_doapp.data.entity.TaskEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskEntity.class, ListEntity.class}, version = 2, exportSchema = false)
public abstract class TestAppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract ListDao listDao();

    public static volatile TestAppDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TestAppDatabase getDatabase(final Context context){

        if(INSTANCE == null){
            synchronized (AppDatabase.class) {
                if(INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TestAppDatabase.class, "task_database").build();

                }
            }
        }
        return INSTANCE;
    }
}

