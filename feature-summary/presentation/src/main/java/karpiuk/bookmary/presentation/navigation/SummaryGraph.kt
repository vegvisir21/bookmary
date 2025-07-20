package karpiuk.bookmary.presentation.navigation

import karpiuk.bookmary.core_ui.base.BaseGraph

sealed class SummaryGraph(override val route: String) : BaseGraph {

    data object Summary : SummaryGraph("summary_graph")

}