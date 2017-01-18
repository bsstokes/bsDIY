package com.bsstokes.bsdiy.api

import com.bsstokes.bsdiy.api.DiyApi.Helper.normalizeUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class DiyApiHelperTest {

    @Test
    fun normalizesEmptyStringToEmptyString() {
        assertEquals("", normalizeUrl(""))
    }

    @Test
    fun normalizesHttpUrlToItself() {
        val url = "http://example.com/"
        assertEquals(url, normalizeUrl(url))
    }

    @Test
    fun normalizesHttpsUrlToItself() {
        val url = "https://example.com/"
        assertEquals(url, normalizeUrl(url))
    }


    @Test
    fun normalizesMissingSchemeToHttps() {
        val url = "//example.com/"
        assertEquals("https://example.com/", normalizeUrl(url))
    }
}
