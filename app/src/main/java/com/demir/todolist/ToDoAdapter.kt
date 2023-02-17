package com.demir.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demir.todolist.databinding.TodoItemBinding
import java.time.format.DateTimeFormatter


class ToDoAdapter:RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    class ToDoViewHolder(val binding: TodoItemBinding,val context:Context):RecyclerView.ViewHolder(binding.root)
    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        private val diffUtil=object :DiffUtil.ItemCallback<ToDoModel>(){
            override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.id==newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem == newItem
            }

        }
        val differ=AsyncListDiffer(this,diffUtil)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view=TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoViewHolder(view,parent.context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo=differ.currentList[position]!!
        holder.binding.name.text=todo.name
        if (todo.isCompleted()){
            holder.binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.binding.completeButton.setImageResource(todo.imageResource())
        holder.binding.completeButton.setColorFilter(todo.imageColor(holder.context))
        holder.binding.completeButton.setOnClickListener{
            onlickComplete?.let { comp->
                comp.invoke(todo)
            }
        }
        holder.binding.taskCellContainer.setOnClickListener{
            onlickEdit?.let { edit->
                edit.invoke(todo)
            }
        }

        if(todo.dueTime() != null)
            holder.binding.dueTime.text = timeFormat.format(todo.dueTime())
        else
            holder.binding.dueTime.text = ""
    }
    var onlickComplete:((ToDoModel)-> Unit)? = null
    var onlickEdit:((ToDoModel)-> Unit)? = null



    override fun getItemCount(): Int {
        return differ.currentList.size

    }
}