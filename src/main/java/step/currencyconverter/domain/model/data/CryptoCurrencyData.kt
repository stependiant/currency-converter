package step.currencyconverter.domain.model.data

data class CryptoCurrencyData(
    val id: String = "",
    val name: String = "",
    val symbol: String = "",
    val rank: Int = 0,
    val quotes: Map<String, Quotes>? = null
) {
    data class Quotes(
        val price: Double = 0.0,
        val volume_24h: Double = 0.0,
        val volume_24h_change_24h: Double = 0.0,
        val market_cap: Double = 0.0,
        val market_cap_change_24h: Double = 0.0,
        val percent_change_15m: Double = 0.0,
        val percent_change_30m: Double = 0.0,
        val percent_change_1h: Double = 0.0,
        val percent_change_6h: Double = 0.0,
        val percent_change_12h: Double = 0.0,
        val percent_change_24h: Double = 0.0,
        val percent_change_7d: Double = 0.0,
        val percent_change_30d: Double = 0.0,
        val percent_change_1y: Double = 0.0,
        val ath_price: Double = 0.0,
        val ath_date: String = "",
        val percent_from_price_ath: Double = 0.0
    )
}
