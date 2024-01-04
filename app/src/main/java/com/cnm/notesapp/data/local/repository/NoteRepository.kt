package com.cnm.notesapp.data.local.repository

import com.cnm.notesapp.data.local.NoteDao
import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : Repository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteByID(id: Long): Flow<Note> {
        return noteDao.getNoteByID(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(id: Long) {
        return noteDao.deleteNote(id)
    }

    override fun getBookmarkedNotes(): Flow<List<Note>> {
        return noteDao.getBookmarkedNotes()
    }
}