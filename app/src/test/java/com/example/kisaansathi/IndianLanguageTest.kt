package com.example.kisaansathi

import com.example.kisaansathi.model.IndianLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class IndianLanguageTest {

    @Test
    fun allTwentyTwoScheduledLanguagesPlusEnglishAreListed() {
        assertEquals(23, IndianLanguage.entries.size)
        assertEquals(IndianLanguage.entries.size, IndianLanguage.entries.map { it.code }.toSet().size)
    }

    @Test
    fun fromCodeFallsBackToEnglish() {
        assertEquals(IndianLanguage.TAMIL, IndianLanguage.fromCode("ta"))
        assertEquals(IndianLanguage.ENGLISH, IndianLanguage.fromCode("xx"))
        assertEquals(IndianLanguage.ENGLISH, IndianLanguage.fromCode(null))
    }

    @Test
    fun voiceIsOnlyEnglishOrHindi() {
        for (lang in IndianLanguage.entries) {
            assertTrue("${lang.code} -> ${lang.speechTag}", lang.speechTag == "en-IN" || lang.speechTag == "hi-IN")
        }
    }
}
