package com.cnm.notesapp.domain.usecases

import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredBookmarkedNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getBookmarkedNotes()
    }
}