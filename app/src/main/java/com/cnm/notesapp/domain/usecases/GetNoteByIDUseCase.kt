package com.cnm.notesapp.domain.usecases

import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteByIDUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Long): Flow<Note> = repository.getNoteByID(id)
}