package com.iliaziuzin.exchangeratechecker.data

import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity
import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair

fun FavoriteCurrenciesPair.toFavoriteCurrencyPairEntity():FavoriteCurrencyPairEntity {
    return FavoriteCurrencyPairEntity(
        from = from,
        to = to
    )
}

fun FavoriteCurrencyPairEntity.toFavoriteCurrenciesPair():FavoriteCurrenciesPair {
    return FavoriteCurrenciesPair(
        from = from,
        to = to
    )
}



