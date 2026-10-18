package com.example.kisaansathi

import com.example.kisaansathi.data.Translations
import com.example.kisaansathi.model.IndianLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TranslationsTest {

    private val translated = IndianLanguage.entries.filter { Translations.supports(it) }

    @Test
    fun englishKeysAreUnique() {
        assertEquals(Translations.english.size, Translations.english.toSet().size)
    }

    @Test
    fun everySupportedLanguageTranslatesEveryKey() {
        for (lang in translated) {
            for (key in Translations.english) {
                val result = Translations.translate(lang, key)
                assertTrue("${lang.code}: blank translation for \"$key\"", result.isNotBlank())
            }
        }
    }

    @Test
    fun translationsActuallyDifferFromEnglishForCommonLabels() {
        for (lang in translated) {
            assertNotEquals("${lang.code} Home", "Home", Translations.translate(lang, "Home"))
            assertNotEquals("${lang.code} Try again", "Try again", Translations.translate(lang, "Try again"))
        }
    }

    @Test
    fun unknownTextAndEnglishAreLeftUntouched() {
        assertEquals("Some unlisted sentence", Translations.translate(IndianLanguage.KANNADA, "Some unlisted sentence"))
        assertEquals("Home", Translations.translate(IndianLanguage.ENGLISH, "Home"))
        assertEquals("Home", Translations.translate(IndianLanguage.HINDI, "Home"))
    }

    @Test
    fun brandNamesSurviveTranslation() {
        for (lang in translated) {
            assertTrue(lang.code, Translations.translate(lang, "Contact Group 11 IDT").contains("Group 11 IDT"))
            assertTrue(lang.code, Translations.translate(lang, "Chat on WhatsApp").contains("WhatsApp"))
        }
    }
}
