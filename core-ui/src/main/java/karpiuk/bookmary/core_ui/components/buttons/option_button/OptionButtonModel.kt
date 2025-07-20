package karpiuk.bookmary.core_ui.components.buttons.option_button

data class OptionButtonModel(
    val title: String = "",
    val onClick: () -> Unit = {},
)