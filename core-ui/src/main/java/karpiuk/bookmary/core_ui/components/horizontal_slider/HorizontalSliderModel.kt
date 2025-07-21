package karpiuk.bookmary.core_ui.components.horizontal_slider

data class HorizontalSliderModel(
    val value: Float,
    val valueRange: ClosedFloatingPointRange<Float>,
    val enabled: Boolean = true,
)