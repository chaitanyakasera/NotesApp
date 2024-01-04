package com.cnm.notesapp.domain.usecases

import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.repository.Repository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}