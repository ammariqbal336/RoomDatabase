package com.ixs.testing.roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [Note::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{

        @Volatile
        private var instance : AppDataBase? = null
        private val LOCK  = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context)  = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "noteDB"
        ).build()
    }
}