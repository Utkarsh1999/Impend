package com.impend.shared.util

object CurrencyFormatter {
    
    fun format(amount: Double, currencyCode: String): String {
        val wholePart = amount.toLong()
        val fractionalPart = kotlin.math.round((amount - wholePart) * 100).toInt()
        val formattedNumber = "$wholePart.${fractionalPart.toString().padStart(2, '0')}"

        return when (currencyCode.uppercase()) {
            "USD" -> "$$formattedNumber"
            "EUR" -> "€${formattedNumber.replace('.', ',')}"
            "GBP" -> "£$formattedNumber"
            "JPY" -> "¥$wholePart" // JPY typically doesn't use fractional currencies in display
            "INR" -> "₹$formattedNumber"
            else -> "$currencyCode $formattedNumber" // Fallback
        }
    }
}
