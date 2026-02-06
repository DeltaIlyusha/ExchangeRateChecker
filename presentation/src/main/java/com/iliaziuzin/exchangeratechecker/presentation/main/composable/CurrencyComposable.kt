package com.iliaziuzin.exchangeratechecker.presentation.main.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.ui.theme.FavDisabled
import com.iliaziuzin.exchangeratechecker.ui.theme.FavEnabled
import com.iliaziuzin.exchangeratechecker.ui.theme.bgCard

@Composable
fun CurrencyComposable(
    modifier: Modifier = Modifier,
    code: String,
    rate: String = "",
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.bgCard,
        )

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = code)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = rate)

            Spacer(modifier = Modifier.size(16.dp))
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onFavoriteClick
            ) {
                val iconRes = if (isFavorite) R.drawable.icon_favorites_on else R.drawable.icon_favorites_off
                val tintColor = if (isFavorite) MaterialTheme.colorScheme.FavEnabled else MaterialTheme.colorScheme.FavDisabled

                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = iconRes),
                    tint = tintColor,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
                )
            }
        }
    }


}

@Preview(showBackground = false)
@Composable
fun CurrencyComposablePreview() {
    CurrencyComposable(code = "USD", rate = "1.03452345", isFavorite = true, onFavoriteClick = {})
}
