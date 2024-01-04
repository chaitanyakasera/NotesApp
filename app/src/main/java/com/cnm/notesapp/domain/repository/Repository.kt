package com.cnm.notesapp.domain.repository

import com.cnm.notesapp.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteByID(id: Long): Flow<Note>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)

    fun getBookmarkedNotes(): Flow<List<Note>>

}