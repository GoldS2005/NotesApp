@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.notes.model

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.data.Note

class NotesViewModel: ViewModel() {
    val notes = mutableStateOf(mutableListOf<Note>())

    fun addNote(note:Note) {
        notes.value.add(note)

    }

    fun deleteNote(note: Note) {
        notes.value.remove(note)
    }
}



@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel,
    onBackWardClick: () -> Unit,
    addNote: (Note) -> Unit) {
    val titleState = remember { mutableStateOf("") }
    val textState = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    Column {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackWardClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primaryContainer
                )

            }

        }
        TextField(
            value = titleState.value,
            onValueChange = { titleState.value = it },
            label = { Text("Title") }
        )
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("Text") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newNote = Note(titleState.value, textState.value)
            addNote(newNote)
            titleState.value = ""
            textState.value = ""
            localFocusManager.clearFocus()}
        ) {

            Text("Add Note")

        }

    }
}






