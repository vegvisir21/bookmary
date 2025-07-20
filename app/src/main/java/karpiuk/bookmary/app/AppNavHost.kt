package karpiuk.bookmary.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import karpiuk.bookmary.presentation.navigation.SummaryGraph
import karpiuk.bookmary.presentation.navigation.summaryNavHost

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = SummaryGraph.Summary.route,
) {

    NavHost(
        navController = navController,
        startDestination = SummaryGraph.Summary.route,
    ) {
        summaryNavHost(
            modifier = modifier,
        )
    }

}