package karpiuk.bookmary.core_ui.components.icon

import androidx.annotation.DrawableRes
import karpiuk.bookmary.core_ui.R

enum class AppIconType(
    @DrawableRes val resId: Int,
) {
    FastForward10(R.drawable.ic_fast_forward_10),
    Headphones(R.drawable.ic_headphones),
    Menu(R.drawable.ic_menu),
    Pause(R.drawable.ic_pause),
    Play(R.drawable.ic_play),
    PlayNext(R.drawable.ic_play_next),
    PlayPrevious(R.drawable.ic_play_previous),
    Rewind5(R.drawable.ic_rewind_5),
}