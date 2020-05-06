package com.example.tasktodo.db


import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface NotesDao {

    @Query("SELECT * FROM notesdata")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status: Boolean)
}