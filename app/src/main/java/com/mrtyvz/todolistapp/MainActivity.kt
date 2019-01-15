package com.mrtyvz.todolistapp

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.aniket.mutativefloatingactionbutton.MutativeFab

class MainActivity : AppCompatActivity() {

    val NEW_TODO_ITEM_REQUEST_CODE = 1
    val EDIT_TODO_ITEM_REQUEST_CODE = 2

    var todoItemViewModel:TodoItemViewModel ?= null
    var recyclerView:RecyclerView?=null
    var todoItemAdapter:TodoItemAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoItemViewModel=ViewModelProviders.of(this).get(TodoItemViewModel::class.java)

        recyclerView=findViewById(R.id.activity_main_recyclerView_todoItems)
        todoItemAdapter= TodoItemAdapter(this, todoItemViewModel!!)
        recyclerView?.layoutManager=LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter=todoItemAdapter

        todoItemViewModel!!.getAll().observe(this, Observer<List<TodoItem>> { list ->
            //update recyclerView
            todoItemAdapter?.submitList(list!!)
        })

        val fab : MutativeFab = findViewById(R.id.activity_main_fab_add)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity,AddTodoItemActivity::class.java)
            startActivityForResult(intent,NEW_TODO_ITEM_REQUEST_CODE)
        }

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0
        , ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder!!.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    todoItemViewModel!!.delete(todoItemAdapter!!.getTodoItemAt(position))
                    Toast.makeText(this@MainActivity, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }
        }
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

        todoItemAdapter!!.setOnItemClickListener(object:TodoItemAdapter.OnItemClickListener{
            override fun onItemClick(todoItem: TodoItem) {
                val intent = Intent(this@MainActivity,AddTodoItemActivity::class.java)
                intent.putExtra(AddTodoItemActivity.EXTRA_ID,todoItem.id)
                intent.putExtra(AddTodoItemActivity.EXTRA_TITLE,todoItem.title)
                intent.putExtra(AddTodoItemActivity.EXTRA_DESCRIPTION,todoItem.description)
                intent.putExtra(AddTodoItemActivity.EXTRA_PRIORITY,todoItem.priority)
                startActivityForResult(intent,EDIT_TODO_ITEM_REQUEST_CODE)
            }

        })

        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==NEW_TODO_ITEM_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            val title = data!!.getStringExtra(AddTodoItemActivity.EXTRA_TITLE)
            val desc = data.getStringExtra(AddTodoItemActivity.EXTRA_DESCRIPTION)
            val priority : Int = data.getIntExtra(AddTodoItemActivity.EXTRA_PRIORITY,1)

            val todoItem = TodoItem(title,desc,priority)
            todoItemViewModel!!.insert(todoItem)

            Toast.makeText(applicationContext,"Saved",Toast.LENGTH_SHORT).show()
        }else if (requestCode==EDIT_TODO_ITEM_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            val id : Int = data!!.getIntExtra(AddTodoItemActivity.EXTRA_ID,-1)
            if (id==-1){
                Toast.makeText(this@MainActivity,"Item can not be updated",Toast.LENGTH_SHORT).show()
                return
            }else {
                val title = data!!.getStringExtra(AddTodoItemActivity.EXTRA_TITLE)
                val desc = data.getStringExtra(AddTodoItemActivity.EXTRA_DESCRIPTION)
                val priority : Int = data.getIntExtra(AddTodoItemActivity.EXTRA_PRIORITY,1)

                val todoItem = TodoItem(title,desc,priority)
                todoItem.id=id
                todoItemViewModel!!.update(todoItem)

                Toast.makeText(this@MainActivity,"Item updated",Toast.LENGTH_SHORT).show()
            }


        } else {
            Toast.makeText(applicationContext,"Todo item not saved",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater:MenuInflater=menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.delete_all_items -> {
                todoItemViewModel!!.deleteAll()
                Toast.makeText(applicationContext,"All items deleted",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
