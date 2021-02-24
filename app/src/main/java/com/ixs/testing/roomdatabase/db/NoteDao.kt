package com.ixs.testing.roomdatabase.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun SaveNote(note : Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getNote() :List<Note>

    @Insert
    suspend fun SaveAllNotes(vararg note: Note)

    @Update
    suspend fun UpdateNote(note: Note)


    @Delete
    suspend fun DeleteNote(note: Note)

}