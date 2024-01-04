package com.cnm.notesapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cnm.notesapp.common.ScreenViewState
import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.usecases.DeleteNoteUseCase
import com.cnm.notesapp.domain.usecases.FilteredBookmarkedNotesUseCase
import com.cnm.notesapp.domain.usecases.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val filteredBookmarkedNotesUseCase: FilteredBookmarkedNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()
    init {
        getBookmarkNotes()
    }
    fun getBookmarkNotes() {
        filteredBookmarkedNotesUseCase()
            .onEach {
                _state.value = BookmarkState(
                    notes = ScreenViewState.Success(it)
                )
            }.catch {
                _state.value = BookmarkState(
                    notes = ScreenViewState.Error(it.message ?: "Unknown Error")
                )
            }.launchIn(
                viewModelScope
            )
    }

    fun onBookmarkChange(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(
                note.copy(
                    isBookmarked = !note.isBookmarked
                )
            )
        }
    }

    fun deleteNote(noteID: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteID)
        }
    }
}

data class BookmarkState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading
)