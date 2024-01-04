package com.cnm.notesapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cnm.notesapp.common.ScreenViewState
import com.cnm.notesapp.data.local.model.Note
import com.cnm.notesapp.presentation.home.NoteCard

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    modifier: Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDelete: (id: Long) -> Unit,
    onNoteClicked: (id: Long) -> Unit
) {
     when (state.notes) {
        is ScreenViewState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        is ScreenViewState.Success -> {
            val notes = state.notes.data
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                modifier = modifier
            ) {
                itemsIndexed(notes) { index: Int, item: Note ->
                    NoteCard(
                        index = index,
                        note = item,
                        onBookmarkChange = onBookmarkChange,
                        onDeleteNote = onDelete,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }

        is ScreenViewState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = state.notes.message ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error
                )

            }
        }
    }
}