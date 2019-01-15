package com.mrtyvz.todolistapp

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

/**
 * Created by mrtyvz on 05.01.2019.
 */

public class TodoItemRepository(application: Application){
    private val todoItemDao:TodoItemDao
    private val todoItemList : LiveData<List<TodoItem>>

    init {
        val appDatabase = AppDatabase.getInstance(application)
        todoItemDao=appDatabase.todoItemDao()
        todoItemList=todoItemDao.getAllTodoItems()
    }

    fun insert(todoItem: TodoItem){
        insertAsyncTask(todoItemDao).execute(todoItem)
    }

    fun update(todoItem: TodoItem){
        updateAsyncTask(todoItemDao).execute(todoItem)
    }

    fun delete(todoItem: TodoItem){
        deleteAsyncTask(todoItemDao).execute(todoItem)
    }

    fun deleteAllItems(){
        deleteAllAsyncTask(todoItemDao).execute()
    }

    fun getAllTodoItems():LiveData<List<TodoItem>>{
        return todoItemList
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao : TodoItemDao): AsyncTask<TodoItem, Void, Void>(){
        override fun doInBackground(vararg params: TodoItem): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao : TodoItemDao): AsyncTask<TodoItem, Void, Void>(){
        override fun doInBackground(vararg params: TodoItem): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }
    private class updateAsyncTask internal constructor(private val mAsyncTaskDao : TodoItemDao): AsyncTask<TodoItem, Void, Void>(){
        override fun doInBackground(vararg params: TodoItem): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class deleteAllAsyncTask internal constructor(private val mAsyncTaskDao : TodoItemDao): AsyncTask<TodoItem, Void, Void>(){
        override fun doInBackground(vararg params: TodoItem): Void? {
            mAsyncTaskDao.deleteAllItems()
            return null
        }
    }
}