package karpiuk.bookmary.presentation.navigation

import karpiuk.bookmary.core_ui.base.BaseScreen

internal sealed class Screen(override val route: String) : BaseScreen {

    data object SummaryScreen : Screen("summary")

}