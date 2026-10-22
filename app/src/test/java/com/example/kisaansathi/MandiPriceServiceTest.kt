package com.example.kisaansathi

import com.example.kisaansathi.data.MandiPriceService
import com.example.kisaansathi.model.FarmExpense
import com.example.kisaansathi.model.calculateFarmProfit
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MandiPriceServiceTest {

    @Test
    fun missingApiKeyFailsExplicitlyInsteadOfReturningSamplePrices() = runBlocking {
        val exception = try {
            MandiPriceService.fetchPrices("Wheat", "Haryana", "")
            null
        } catch (error: IOException) {
            error
        }

        assertTrue(exception?.message?.contains("DATA_GOV_IN_API_KEY") == true)
    }

    @Test
    fun profitSummarySeparatesIncomeCostsAndCropTotals() {
        val summary = calculateFarmProfit(
            listOf(
                FarmExpense("1", "Wheat", "Seed", 100.0, false, "2026-10-22"),
                FarmExpense("2", "Wheat", "Grain sale", 300.0, true, "2026-10-22"),
                FarmExpense("3", "Paddy", "Fertilizer", 50.0, false, "2026-10-22")
            )
        )

        assertEquals(300.0, summary.income, 0.0)
        assertEquals(150.0, summary.expenses, 0.0)
        assertEquals(150.0, summary.net, 0.0)
        assertEquals(200.0, summary.netByCrop["Wheat"]!!, 0.0)
        assertEquals(-50.0, summary.netByCrop["Paddy"]!!, 0.0)
    }
}
