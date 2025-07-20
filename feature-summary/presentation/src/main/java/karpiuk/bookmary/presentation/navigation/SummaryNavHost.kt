package karpiuk.bookmary.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import karpiuk.bookmary.presentation.screens.summary.SummaryScreen

fun NavGraphBuilder.summaryNavHost(
    modifier: Modifier = Modifier,
) {

    navigation(
        startDestination = Screen.SummaryScreen.route,
        route = SummaryGraph.Summary.route,
    ) {
        composable(Screen.SummaryScreen.route) {
            SummaryScreen(modifier = modifier)
        }
    }
}