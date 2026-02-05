package com.iliaziuzin.exchangeratechecker.mappers

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.CurrencyExchangePairItem


fun CurrencyExchangePairItem.toFavoriteCurrenciesPair(): FavoriteCurrenciesPair {
    return FavoriteCurrenciesPair(
        from = from,
        to = to,
    )
}

fun FavoriteCurrenciesPair.toCurrencyExchangePairItem(): CurrencyExchangePairItem {
    return CurrencyExchangePairItem(
        from = from,
        to = to,
        rate = 0.0
    )
}

fun CurrencyExchangePair.toCurrencyExchangePairItem(): CurrencyExchangePairItem {
    return CurrencyExchangePairItem(
        from = from,
        to = to,
        rate = rate
    )
}




