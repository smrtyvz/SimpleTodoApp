package com.mrtyvz.todolistapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by mrtyvz on 04.01.2019.
 */

@Database(entities = {TodoItem.class},version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String TABLE_NAME_TODO_ITEM="todoItem";
    public static final String DATABASE_NAME = "application_database";

    private static AppDatabase INSTANCE;

    public abstract TodoItemDao todoItemDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}