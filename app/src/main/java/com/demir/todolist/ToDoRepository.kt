package com.demir.todolist

import android.app.Application
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class ToDoRepository(val application:Application)
{
    private val db=ToDoDataBase
    private val toDoDao=db.getDatabase(application).taskItemDao()
    val allTaskItems: Flow<List<ToDoModel>> = toDoDao.allTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(toDoModel: ToDoModel)
    {
        toDoDao.insertTaskItem(toDoModel)
    }

    @WorkerThread
    suspend fun updateTaskItem(toDoModel: ToDoModel)
    {
        toDoDao.updateTaskItem(toDoModel)
    }

    @WorkerThread
    suspend fun deleteTaskItem(todoModel:ToDoModel){
        toDoDao.deleteTaskItem(todoModel)
    }

}