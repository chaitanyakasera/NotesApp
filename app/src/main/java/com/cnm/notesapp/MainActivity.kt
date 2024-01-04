package com.cnm.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.cnm.notesapp.presentation.bookmark.BookmarkViewModel
import com.cnm.notesapp.presentation.detail.IDetailedAssistedFactory
import com.cnm.notesapp.presentation.home.HomeViewModel
import com.cnm.notesapp.presentation.navigation.NoteNavigation
import com.cnm.notesapp.presentation.navigation.Screens
import com.cnm.notesapp.presentation.navigation.navigateToSingleTop
import com.cnm.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: IDetailedAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    NoteApp()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val bookmarkViewModel: BookmarkViewModel = viewModel()
        val navController = rememberNavController()
        var currentTab by remember {
            mutableStateOf(TabScreen.Home)
        }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            InputChip(
                                selected = currentTab == TabScreen.Home,
                                onClick = {
                                    currentTab = TabScreen.Home
                                    navController.navigateToSingleTop(route = Screens.Home.name)
                                },
                                label = {
                                    Text(text = "Home")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.Companion.size(12.dp))
                            InputChip(
                                selected = currentTab == TabScreen.Bookmark,
                                onClick = {
                                    currentTab = TabScreen.Bookmark
                                    navController.navigateToSingleTop(route = Screens.Bookmark.name)
                                },
                                label = {
                                    Text(text = "Bookmark")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = null
                                    )
                                }
                            )

                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigateToSingleTop(
                                    route = Screens.Detail.name
                                )
                            }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            NoteNavigation(
                navHostController = navController,
                homeViewModel = homeViewModel,
                bookmarkViewModel = bookmarkViewModel,
                assistedFactory = assistedFactory,
                modifier = Modifier.padding(it)
            )
        }
    }

}

enum class TabScreen {
    Home, Bookmark
}