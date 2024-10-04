package com.saschaw.hooked.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.saschaw.hooked.feature.browse.nav.BrowseRoute
import com.saschaw.hooked.feature.browse.nav.browseScreen
import com.saschaw.hooked.ui.HookedAppState

@Suppress("ktlint:standard:function-naming")
/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun HookedNavHost(
    appState: HookedAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = BrowseRoute,
        modifier = modifier,
    ) {
        browseScreen()

        // More routes ...
//        bookmarksScreen(
//            onTopicClick = navController::navigateToInterests,
//            onShowSnackbar = onShowSnackbar,
//        )
//        searchScreen(
//            onBackClick = navController::popBackStack,
//            onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
//            onTopicClick = navController::navigateToInterests,
//        )
//        interestsListDetailScreen()
    }
}
