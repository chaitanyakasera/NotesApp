package com.cnm.notesapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cnm.notesapp.common.ScreenViewState
import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.domain.usecases.DeleteNoteUseCase
import com.cnm.notesapp.domain.usecases.GetAllNotesUseCase
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
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNotesUseCase: UpdateNoteUseCase
) : ViewModel() {
    private var _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateHome: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getAllNotesUseCase()
            .onEach {
                _homeState.value = HomeState(notes = ScreenViewState.Success(it))
            }.catch {
                _homeState.value = HomeState(notes = ScreenViewState.Error(it.message))
            }.launchIn(
                viewModelScope
            )
    }

    fun deleteANote(id: Long) = viewModelScope.launch {
        deleteNoteUseCase(id)
    }

    fun onBookMarkChange(note: Note) {
        viewModelScope.launch {
            updateNotesUseCase(note.copy(isBookmarked = !note.isBookmarked))
        }
    }

}

data class HomeState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading
)


