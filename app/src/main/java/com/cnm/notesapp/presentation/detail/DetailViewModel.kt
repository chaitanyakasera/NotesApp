package com.cnm.notesapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.usecases.AddUseCase
import com.cnm.notesapp.domain.usecases.GetNoteByIDUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel @AssistedInject constructor(
    private val addUseCase: AddUseCase,
    private val getNoteByIDUseCase: GetNoteByIDUseCase,
    @Assisted private val noteID: Long
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    val isFormNotBlank: Boolean
        get() = state.title.isNotEmpty() &&
                state.content.isNotEmpty()

    private val note: Note
        get() = state.run {
            Note(
                id = id,
                title = title,
                content = content,
                createdDate = createdAt,
                isBookmarked = isBookmarked
            )
        }

    init {
        initialize()
    }

    private fun initialize() {
        val isUpdatingNote = noteID != -1L
        state = state.copy(isUpdatingNote = isUpdatingNote)
        if (isUpdatingNote) {
            getNoteByID()
        }
    }

    fun onTitleChange(title: String) {
        state = state.copy(title = title)
    }

    fun onContentChange(content: String) {
        state = state.copy(content = content)
    }

    fun onBookmarkChange(isBooked: Boolean) {
        state = state.copy(isBookmarked = isBooked)
    }

    fun addOrUpdateNote() {
        viewModelScope.launch {
            addUseCase(note = note)
        }
    }

    private fun getNoteByID() =
        viewModelScope.launch {
            getNoteByIDUseCase(noteID).collectLatest { note ->
                state = state.copy(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    isBookmarked = note.isBookmarked,
                    createdAt = note.createdDate
                )
            }
        }

}

data class DetailState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val isBookmarked: Boolean = false,
    val createdAt: Date = Date(),
    val isUpdatingNote: Boolean = false
)

class DetailedViewModelFactory(
    private val noteID: Long,
    private val assistedFactory: IDetailedAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(noteID) as T

    }
}

@AssistedFactory
interface IDetailedAssistedFactory {
    fun create(noteID: Long): DetailViewModel
}