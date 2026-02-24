package com.impend.shared.util

import kotlin.test.Test
import kotlin.test.assertEquals

class CurrencyFormatterTest {

    @Test
    fun formatsUsdCorrectly() {
        assertEquals("$150.50", CurrencyFormatter.format(150.5, "USD"))
        assertEquals("$10.00", CurrencyFormatter.format(10.0, "USD"))
    }

    @Test
    fun formatsEurCorrectly() {
        assertEquals("€150,50", CurrencyFormatter.format(150.5, "EUR"))
    }

    @Test
    fun formatsGbpCorrectly() {
        assertEquals("£99.99", CurrencyFormatter.format(99.99, "GBP"))
    }

    @Test
    fun formatsJpyCorrectly() {
        assertEquals("¥5000", CurrencyFormatter.format(5000.75, "JPY")) // Truncates decimal
    }

    @Test
    fun formatsInrCorrectly() {
        assertEquals("₹1000.50", CurrencyFormatter.format(1000.5, "INR"))
    }
    
    @Test
    fun formatsFallbackCorrectly() {
         assertEquals("AUD 50.25", CurrencyFormatter.format(50.25, "AUD"))
    }
}
