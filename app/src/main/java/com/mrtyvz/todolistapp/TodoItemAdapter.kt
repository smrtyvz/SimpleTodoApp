package com.mrtyvz.todolistapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by mrtyvz on 06.01.2019.
 */
class TodoItemAdapter internal constructor(context:Context, todoItemViewModel: TodoItemViewModel): ListAdapter<TodoItem,TodoItemAdapter.TodoItemViewHolder>(DiffCallBack()) {

    private val mInflater:LayoutInflater
    private var todoItemList:List<TodoItem>?=null
    private var todoItemViewModel:TodoItemViewModel?=null
    private var listener:OnItemClickListener?=null

    init {
        mInflater= LayoutInflater.from(context)
        this.todoItemViewModel=todoItemViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val itemView = mInflater.inflate(R.layout.todo_item,parent,false)
        return TodoItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.txtViewTitle.text=todoItem.title
        holder.txtViewDesc.text=todoItem.description
        when(todoItem.priority){
            1-> holder.txtViewPriority.setBackgroundResource(R.drawable.todo_item_priority_bg_red)
            2->holder.txtViewPriority.setBackgroundResource(R.drawable.todo_item_priority_bg_orange)
            3->holder.txtViewPriority.setBackgroundResource(R.drawable.todo_item_priority_bg_green)
        }
        holder.txtViewPriority.text= todoItem.priority.toString()
    }

    fun getTodoItemAt(position: Int):TodoItem{
        return getItem(position)
    }


    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewTitle:TextView
        val txtViewDesc:TextView
        val txtViewPriority:TextView

        init {
            txtViewTitle=itemView.findViewById(R.id.todo_item_textView_title)
            txtViewDesc=itemView.findViewById(R.id.todo_item_textView_description)
            txtViewPriority=itemView.findViewById(R.id.todo_item_textView_priority)

            itemView.setOnClickListener {
                val position:Int = adapterPosition
                if (listener!=null && position!=RecyclerView.NO_POSITION)
                listener!!.onItemClick(getItem(position))
            }
        }

    }

    public interface OnItemClickListener{
        fun onItemClick(todoItem: TodoItem)
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener=listener
    }

    class DiffCallBack : DiffUtil.ItemCallback<TodoItem>(){
        override fun areItemsTheSame(oldItem: TodoItem?, newItem: TodoItem?): Boolean {
            return oldItem!!.id==newItem!!.id
        }

        override fun areContentsTheSame(oldItem: TodoItem?, newItem: TodoItem?): Boolean {
            return oldItem!!.title==newItem!!.title
                    && oldItem.description==newItem.description
                    && oldItem.priority==newItem.priority
        }

    }
}