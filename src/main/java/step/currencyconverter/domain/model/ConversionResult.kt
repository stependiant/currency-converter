package step.currencyconverter.domain.model

data class ConversionResult(
    val from: String = "",
    val to: String = "",
    val originalAmount: Double = 0.0,
    val convertedAmount: Double = 0.0
)
