package com.mrtyvz.todolistapp

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by mrtyvz on 04.01.2019.
 */
@Dao
interface TodoItemDao {

    @Insert
    fun insert(todoItem: TodoItem):Long

    @Insert
    fun insertList(todoList:List<TodoItem>)

    @Delete
    fun delete(todoItem: TodoItem):Int

    @Update
    fun update(todoItem: TodoItem)

    @Query("DELETE FROM " + AppDatabase.TABLE_NAME_TODO_ITEM)
    fun deleteAllItems()

    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_TODO_ITEM)
    fun getAllTodoItems(): LiveData<List<TodoItem>>

}