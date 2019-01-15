package com.mrtyvz.todolistapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddTodoItemActivity : AppCompatActivity() {

    var edtTxtTitle:EditText?=null
    var edtTxtDesc:EditText?=null
    var priorityPicker:NumberPicker?=null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater:MenuInflater=menuInflater
        menuInflater.inflate(R.menu.add_todo_item_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_todo_item->{
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

     fun saveNote(){
         val title = edtTxtTitle!!.text.toString()
         val desc = edtTxtDesc!!.text.toString()
         val priority : Int = priorityPicker!!.value

         if (title.trim().isEmpty() || desc.trim().isEmpty()){
             Toast.makeText(this,"Please insert a title and description",Toast.LENGTH_SHORT).show()
             return
         }

         val data=Intent()
         data.putExtra(EXTRA_TITLE,title)
         data.putExtra(EXTRA_DESCRIPTION,desc)
         data.putExtra(EXTRA_PRIORITY,priority)

         val id : Int =intent.getIntExtra(EXTRA_ID,-1)
         if (id!=-1){
             data.putExtra(EXTRA_ID,id)
         }

         setResult(RESULT_OK,data)
         finish()
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo_item)

        edtTxtTitle=findViewById(R.id.activity_addTodoItem_edtTxt_title)
        edtTxtDesc=findViewById(R.id.activity_addTodoItem_edtTxt_desc)
        priorityPicker=findViewById(R.id.activity_addTodoItem_numPckr_priority)

        priorityPicker!!.maxValue=3
        priorityPicker!!.minValue=1

        val intent:Intent=getIntent()
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Todo Item")
            edtTxtTitle!!.setText(intent.getStringExtra(EXTRA_TITLE))
            edtTxtDesc!!.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            priorityPicker!!.value=intent.getIntExtra(EXTRA_PRIORITY,1)
        }else{
            setTitle("Add Todo Item")
        }


    }

    companion object {
        const val EXTRA_TITLE = "com.mrtyvz.todolistapp.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.mrtyvz.todolistapp.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.mrtyvz.todolistapp.EXTRA_PRIORITY"
        const val EXTRA_ID = "com.mrtyvz.todolistapp.EXTRA_ID"
    }
}
