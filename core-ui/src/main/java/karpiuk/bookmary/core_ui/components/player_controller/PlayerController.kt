package karpiuk.bookmary.core_ui.components.player_controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.components.buttons.icon_button.PrimaryIconButton
import karpiuk.bookmary.core_ui.components.buttons.icon_button.PrimaryIconButtonModel
import karpiuk.bookmary.core_ui.components.icon.AppIconType
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun PlayerController(
    model: PlayerControllerModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PrimaryIconButton(
            model = PrimaryIconButtonModel.large(
                icon = AppIconType.PlayPrevious,
                onClick = model.onPlayPreviousClick,
            )
        )
        PrimaryIconButton(
            model = PrimaryIconButtonModel.large(
                icon = AppIconType.Rewind5,
                onClick = model.onRewind,
            )
        )
        if (model.isPlaying) {
            PrimaryIconButton(
                model = PrimaryIconButtonModel.large(
                    icon = AppIconType.Pause,
                    onClick = model.onPauseClick,
                )
            )
        } else {
            PrimaryIconButton(
                model = PrimaryIconButtonModel.large(
                    icon = AppIconType.Play,
                    onClick = model.onPlayClick,
                )
            )
        }
        PrimaryIconButton(
            model = PrimaryIconButtonModel.large(
                icon = AppIconType.FastForward10,
                onClick = model.onForward,
            )
        )
        PrimaryIconButton(
            model = PrimaryIconButtonModel.large(
                icon = AppIconType.PlayNext,
                onClick = model.onPlayNextClick,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerControllerPreview() {
    BookmaryTheme {
        PlayerController(
            model = PlayerControllerModel(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerControllerPlayingPreview() {
    BookmaryTheme {
        PlayerController(
            model = PlayerControllerModel(
                isPlaying = true,
            ),
        )
    }
}
