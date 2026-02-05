package com.iliaziuzin.exchangeratechecker.data.local

import androidx.room.Entity
import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode

@Entity(tableName = "favorite_currency_pairs", primaryKeys = ["from", "to"])
data class FavoriteCurrencyPairEntity(
    val from: CurrencyCode,
    val to: CurrencyCode,
)
