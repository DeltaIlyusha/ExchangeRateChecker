package com.iliaziuzin.exchangeratechecker.mappers

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePairWithFavorite
import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair


fun UiCurrencyExchangePair.toFavoriteCurrenciesPair(): FavoriteCurrenciesPair {
    return FavoriteCurrenciesPair(
        from = from,
        to = to,
    )
}

fun CurrencyExchangePairWithFavorite.toCurrencyExchangePairItem(): UiCurrencyExchangePair {
    return UiCurrencyExchangePair(
        from = from,
        to = to,
        rate = rate,
        isFavorite = isFavorite
    )
}




