package com.cnm.notesapp.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteID: Long,
    assistedFactory: IDetailedAssistedFactory,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = DetailViewModel::class.java,
        factory = DetailedViewModelFactory(
            noteID = noteID,
            assistedFactory = assistedFactory
        )
    )
    val state = viewModel.state
    DetailScreen(
        modifier = modifier,
        isUpdating = state.isUpdatingNote,
        title = state.title,
        content = state.content,
        isBookmark = state.isBookmarked,
        onBookmarkChange = viewModel::onBookmarkChange,
        isFormNotBlank = viewModel.isFormNotBlank,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onButtonClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigateUp = navigateUp
    )

}


@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdating: Boolean,
    title: String,
    content: String,
    isBookmark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TopSection(
            title = title,
            isBookmark = isBookmark,
            onBookmarkChange = onBookmarkChange,
            onTitleChange = onTitleChange,
            onNavigateUp = onNavigateUp,
        )

        Spacer(modifier = Modifier.Companion.size(12.dp))

        AnimatedVisibility(visible = isFormNotBlank) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val icon = if (isUpdating) Icons.Default.Update else Icons.Default.Check
                IconButton(onClick = { onButtonClick() }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.Companion.size(8.dp))

        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            onValueChange = onContentChange,
            label = "Content"
        )
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookmark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigateUp() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = title,
            onValueChange = onTitleChange,
            label = "Title",
            labelAlignment = TextAlign.Center
        )
        IconButton(onClick = { onBookmarkChange(!isBookmark) }) {
            val icon = if (isBookmark) {
                Icons.Default.BookmarkRemove
            } else {
                Icons.Default.BookmarkAdd
            }
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }

    }

}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlignment: TextAlign? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlignment,
                modifier = modifier.fillMaxWidth()
            )
        }

    )

}