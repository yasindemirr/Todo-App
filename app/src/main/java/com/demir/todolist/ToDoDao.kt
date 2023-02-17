package com.demir.todolist

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoDao
{
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTaskItems(): Flow<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: ToDoModel)

    @Update
    suspend fun updateTaskItem(taskItem: ToDoModel)

    @Delete
    suspend fun deleteTaskItem(taskItem: ToDoModel)
}