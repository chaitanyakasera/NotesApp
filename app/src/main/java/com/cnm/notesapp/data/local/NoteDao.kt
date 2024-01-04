package com.cnm.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cnm.notesapp.data.local.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao {
    @Query("Select * from note order by createdDate")
    fun getAllNotes(): Flow<List<Note>>

    @Query("Select * from note where id = :id order by createdDate")
    fun getNoteByID(id: Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("delete from note where id= :id")
    suspend fun deleteNote(id: Long)
//    val true = 1

    @Query("select * from note where note.isBookmarked = 1 order by createdDate desc")
    fun getBookmarkedNotes(): Flow<List<Note>>
}