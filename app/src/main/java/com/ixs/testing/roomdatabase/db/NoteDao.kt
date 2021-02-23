package com.ixs.testing.roomdatabase.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun SaveNote(note : Note)

    @Query("SELECT * FROM note")
    fun getNote() :List<Note>

    @Insert
    fun SaveAllNotes(vararg note: Note)

}