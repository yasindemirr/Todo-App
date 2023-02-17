package com.demir.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoModel::class], version = 1, exportSchema = false)
public abstract class ToDoDataBase : RoomDatabase()
{
    abstract fun taskItemDao(): ToDoDao

    companion object
    {
        @Volatile
        private var INSTANCE: ToDoDataBase? = null

        fun getDatabase(context: Context): ToDoDataBase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDataBase::class.java,
                    "task_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}