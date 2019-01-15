package com.mrtyvz.todolistapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

/**
 * Created by mrtyvz on 05.01.2019.
 */

class TodoItemViewModel(application: Application) : AndroidViewModel(application){

    private val todoRepository : TodoItemRepository
    internal val allTodoItems:LiveData<List<TodoItem>>

    init {
        todoRepository= TodoItemRepository(application)
        allTodoItems=todoRepository.getAllTodoItems()
    }

    fun insert(todoItem: TodoItem){todoRepository.insert(todoItem)}

    fun delete(todoItem: TodoItem){todoRepository.delete(todoItem)}

    fun update(todoItem: TodoItem){todoRepository.update(todoItem)}

    fun deleteAll(){todoRepository.deleteAllItems()}

    fun getAll():LiveData<List<TodoItem>>{return allTodoItems}

}