package com.cnm.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cnm.notesapp.presentation.bookmark.BookmarkScreen
import com.cnm.notesapp.presentation.bookmark.BookmarkViewModel
import com.cnm.notesapp.presentation.detail.DetailScreen
import com.cnm.notesapp.presentation.detail.IDetailedAssistedFactory
import com.cnm.notesapp.presentation.home.HomeScreen
import com.cnm.notesapp.presentation.home.HomeViewModel


enum class Screens {
    Home, Detail, Bookmark
}

@Composable
fun NoteNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    bookmarkViewModel: BookmarkViewModel,
    assistedFactory: IDetailedAssistedFactory
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Home.name,
    ) {
        composable(
            route = Screens.Home.name
        ) {
            val state by homeViewModel.stateHome.collectAsState()
            HomeScreen(
                state = state,
                onBookmarkChange = homeViewModel::onBookMarkChange,
                onDeleteNote = homeViewModel::deleteANote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(
            route = Screens.Bookmark.name
        ) {
            val state by bookmarkViewModel.state.collectAsState()
            BookmarkScreen(
                state = state,
                modifier = modifier,
                onBookmarkChange = bookmarkViewModel::onBookmarkChange,
                onDelete = bookmarkViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(
            route = "${Screens.Detail.name}?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreen(
                noteID = id,
                assistedFactory = assistedFactory,
                modifier = modifier
            ) {
                navHostController.navigateUp()
            }
        }
    }

}


fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}