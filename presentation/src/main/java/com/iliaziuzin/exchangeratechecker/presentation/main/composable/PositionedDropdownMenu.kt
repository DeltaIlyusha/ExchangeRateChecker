package com.iliaziuzin.exchangeratechecker.presentation.main.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.Secondary

@Composable
fun PositionedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    anchorSize: IntSize,
    anchorPosition: IntOffset,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val popupPositionProvider = remember(anchorPosition) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                return IntOffset(
                    x = anchorPosition.x,
                    y = anchorPosition.y
                )
            }
        }
    }

    if (expanded) {
        Popup(
            popupPositionProvider = popupPositionProvider,
            onDismissRequest = onDismissRequest
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.Secondary),
                modifier = modifier
                    .width(with(LocalDensity.current) { anchorSize.width.toDp() })
                    .heightIn(max = 216.dp)
                    .background(MaterialTheme.colorScheme.BackgroundHeader)
            ) {
                content()
            }
        }
    }
}
