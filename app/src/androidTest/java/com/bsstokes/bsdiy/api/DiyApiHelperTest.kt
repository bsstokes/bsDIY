package com.bsstokes.bsdiy.api

import android.support.test.runner.AndroidJUnit4
import com.bsstokes.bsdiy.api.DiyApi.Helper.normalizeUrl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
