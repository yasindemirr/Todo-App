package com.demir.todolist

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: ToDoViewModel by viewModels()
    private lateinit  var todoadapter:ToDoAdapter


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        //taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.addTask.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        editTask()
        completeTask()
        deleteItem()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteItem() {
        binding.deleteTask.setOnClickListener {
            todoadapter.differ.currentList.forEach {
                it?.let { toDoModel ->
                    if (toDoModel.isCompleted()) {
                        taskViewModel.deleteTaskItem(toDoModel)
                        Toast.makeText(this,"Deleted succesfully",Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }


    private fun setRecyclerView() {
        todoadapter= ToDoAdapter()
        binding.todoRec.apply {
            layoutManager=LinearLayoutManager(applicationContext)
            adapter=todoadapter
        }
        taskViewModel.taskItems.observe(this, Observer {
            todoadapter.differ.submitList(it)

        })

        }


     fun editTask(){
         todoadapter.onlickEdit={
             NewTaskSheet(it).show(supportFragmentManager,"newTaskTag")
         }

     }
    @RequiresApi(Build.VERSION_CODES.O)
    fun completeTask(){
        todoadapter.onlickComplete={
            taskViewModel.setCompleted(it)
        }
    }

}