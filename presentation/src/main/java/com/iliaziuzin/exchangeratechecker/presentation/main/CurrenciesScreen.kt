package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundDefault
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.Outline
import com.iliaziuzin.exchangeratechecker.ui.theme.Primary
import com.iliaziuzin.exchangeratechecker.ui.theme.Secondary
import java.text.DecimalFormat
import kotlin.math.roundToInt


@Composable
fun CurrenciesScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onFavoriteClick: (UiCurrencyExchangePair) -> Unit,
    onSortClick: () -> Unit,
    onCurrencySelected: (String) -> Unit
) {
    Scaffold(modifier = modifier.background(MaterialTheme.colorScheme.BackgroundDefault)) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            CurrenciesHeader(
                modifier = Modifier.padding(paddingValues),
                selectedCurrency = uiState.selectedCurrency,
                currencySymbols = uiState.currencySymbols,
                onSortClick = onSortClick,
                onCurrencySelected = onCurrencySelected
            )
            if (uiState.isLoading) {
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxSize().background(MaterialTheme.colorScheme.BackgroundDefault),
                    visible = true,
                    enter = EnterTransition.None,
                    exit = fadeOut(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.BackgroundDefault)
                            .wrapContentSize(),
                        trackColor = MaterialTheme.colorScheme.Primary,

                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.background(MaterialTheme.colorScheme.BackgroundDefault).weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = uiState.currencyExchangePairs, key = { it.key }) { it ->
                        val decimalFormat = DecimalFormat("#.######")
                        CurrencyComposable(
                            code = it.to,
                            rate = decimalFormat.format(it.rate),
                            isFavorite = it.isFavorite,
                            onFavoriteClick = { onFavoriteClick(it) })
                    }
                }
            }
         }
        }
    }


@Composable
fun CurrenciesHeader(modifier: Modifier = Modifier,
                     selectedCurrency:String,
                     currencySymbols:List<String>,
                     onSortClick: () -> Unit,
                     onCurrencySelected: (String) -> Unit
                     ) {
        Box(
            Modifier.background(MaterialTheme.colorScheme.BackgroundHeader)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Currencies",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 42.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
                    var textFieldPosition by remember { mutableStateOf(IntOffset.Zero) }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .onSizeChanged {
                                textFieldSize = it
                            }
                            .onGloballyPositioned {
                                val positionInWindow = it.positionInWindow()
                                textFieldPosition = IntOffset(positionInWindow.x.roundToInt(), positionInWindow.y.roundToInt())
                            }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { expanded = !expanded }
                            ),
                        contentAlignment = Alignment.TopStart

                    ) {
                        OutlinedTextField(
                            value = selectedCurrency,
                            textStyle = MaterialTheme.typography.bodySmall,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            trailingIcon = {
                                if (!expanded) {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_arrow_down),
                                        contentDescription = "Currencies dropdown",
                                    )
                                }
                                },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledContainerColor = Color.Transparent,
                                disabledBorderColor = MaterialTheme.colorScheme.Secondary,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.Primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        PositionedDropdownMenu(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.BackgroundDefault).heightIn(max = 216.dp),
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            anchorSize = textFieldSize,
                            anchorPosition = textFieldPosition
                        ) {
                            Column {
                                currencySymbols.fastForEachIndexed { index, symbol ->
                                    DropdownMenuItem(
                                        modifier = Modifier.background(MaterialTheme.colorScheme.BackgroundDefault),
                                        trailingIcon = {
                                            if (index == 0) {
                                                Icon(
                                                    painter = painterResource(R.drawable.icon_arrow_up),
                                                    tint = MaterialTheme.colorScheme.Primary,
                                                    contentDescription = "Dropdown icon",
                                                ) }
                                            },
                                        text = {
                                            Text(
                                                modifier = Modifier.padding(start = 4.dp, end = 24.dp, top = 18.dp, bottom = 18.dp),
                                                text = symbol,
                                                style = MaterialTheme.typography.bodySmall,
                                            )
                                        },
                                        onClick = {
                                            onCurrencySelected(symbol)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    OutlinedIconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = onSortClick,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.Secondary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_filters),
                            contentDescription = "Sort currencies",
                            tint = MaterialTheme.colorScheme.Primary
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .size(1.dp)
                    .background(color = MaterialTheme.colorScheme.Outline))
            }

        }
}

@Composable
private fun PositionedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    anchorSize: IntSize,
    anchorPosition: IntOffset,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val popupPositionProvider = object : PopupPositionProvider {
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

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
    CurrenciesScreen(
        uiState = MainUiState(
            isLoading = true,
            currencySymbols = listOf("USD", "EUR", "RUB"),
            currencyExchangePairs = listOf(
                UiCurrencyExchangePair("USD", "RUB", 1.0, true, "USDRUB"),
                UiCurrencyExchangePair("EUR", "RUB", 1.0, false, "EURRUB"),
                UiCurrencyExchangePair("RUB", "USD", 1.0, false, "RUBUSD"),
            ),
        ),
        onFavoriteClick = {},
        onSortClick = {},
        onCurrencySelected = {}
    )
}
