package org.overlake.mat803.finalproject

import android.util.ArrayMap
import io.finnhub.api.apis.DefaultApi
import io.finnhub.api.infrastructure.ApiClient

class StockApiHelper {
    companion object {
        init {
            ApiClient.apiKey["token"] = "ch7egtpr01qt83gc8tegch7egtpr01qt83gc8tf0"
        }
        val apiClient = DefaultApi()
    }

    fun getStockQuote(symbol: String): Stock {
        val quote = apiClient.quote(symbol)
        return Stock.create(symbol, quote.c ?: Float.NaN , quote.dp ?: Float.NaN, false, Float.NaN, Float.NaN)
    }

}