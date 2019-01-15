package com.mrtyvz.todolistapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by mrtyvz on 04.01.2019.
 */

@Entity(tableName = AppDatabase.TABLE_NAME_TODO_ITEM)
data class TodoItem(
        val title: String,
        val description : String,
        val priority : Int) : Serializable {
        @PrimaryKey(autoGenerate = true)
        var id : Int?=null
}

