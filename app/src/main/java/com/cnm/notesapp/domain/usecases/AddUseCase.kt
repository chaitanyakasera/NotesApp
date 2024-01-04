package com.cnm.notesapp.domain.usecases

import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}