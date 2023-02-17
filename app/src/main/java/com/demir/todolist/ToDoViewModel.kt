package com.demir.todolist

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate


class ToDoViewModel(application: Application): AndroidViewModel(application)
{
    private val repository= ToDoRepository(application)
    val taskItems: LiveData<List<ToDoModel>> = repository.allTaskItems.asLiveData()

    fun addTaskItem(toDoModel: ToDoModel) = viewModelScope.launch {
        repository.insertTaskItem(toDoModel)
    }

    fun updateTaskItem(toDoModel: ToDoModel) = viewModelScope.launch {
        repository.updateTaskItem(toDoModel)
    }

    fun deleteTaskItem(toDoModel: ToDoModel)=viewModelScope.launch {
        repository.deleteTaskItem(toDoModel)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(toDoModel: ToDoModel) = viewModelScope.launch {
        if (!toDoModel.isCompleted())
            toDoModel.completedDateString = ToDoModel.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(toDoModel)
    }
}

