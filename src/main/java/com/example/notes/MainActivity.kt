@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.data.Note
import com.example.notes.model.AddNoteButton
import com.example.notes.model.DeleteNoteButton
import com.example.notes.model.NotesViewModel
import com.example.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesApp()
                }
            }
        }
    }
}



@Composable
fun NotesApp() {
    var currentState by remember {
        mutableStateOf(1)
    }
    val viewModel: NotesViewModel = viewModel()

    when(currentState) {
        1-> {
            Scaffold(topBar = {NotesTopAppBar()}) { it ->
                LazyColumn(contentPadding = it) {
                    items(viewModel.notes.value) { note ->
                        NotesItem(
                            note = note, modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                            onDeleteClick = { viewModel.deleteNote(note) })

                    }
                }

            }
            Display1(Forward = {currentState = 2})


        }
        2-> {

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Display2(Backward = {currentState = 1})
                AddNoteButton()

            }



        }
    }
}

@Composable
fun NotesItem(
    note: Note,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }


    val color by animateColorAsState(targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer)

    Card(modifier = modifier) {
        Column(modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .background(color = color)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))) {
                NoteTitle(note.title)
                Spacer(modifier = Modifier.weight(0.5f))

                NotesItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if(expanded) {
                NoteText(note.text, modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                ))
            }
            Spacer(modifier = Modifier.height(2.dp))
            DeleteNoteButton(onDeleteClick = onDeleteClick)


            
        }

    }
}

@Composable
fun NotesItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    IconButton(onClick =  onClick ) {
        Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = null,
            tint = MaterialTheme.colorScheme.background)
        
    }

}

@Composable
fun NoteTitle(title: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.displaySmall)

    }
}

@Composable
fun NoteText(text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)

    }
}


@Composable
fun NotesTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.displayLarge)


    }  }, modifier = modifier
    )
}

@Composable
fun Display1 (Forward: () -> Unit) {
    Row(verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()) {
        Button(onClick = Forward) {
            Text(text = "Add", textAlign = TextAlign.Center, modifier = Modifier.width(200.dp))

        }
    }

}

@Composable
fun Display2 (Backward: () -> Unit) {
    Row(verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick =  Backward ) {
            Icon(imageVector =  Icons.Filled.ArrowBack, contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer)

        }

        }

    }





@Preview(showBackground = true)
@Composable
fun NotesPreview() {
    NotesTheme {
        NotesApp()
    }
}