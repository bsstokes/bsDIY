package com.bsstokes.bsdiy.db.sqlite.mappers

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteDatabase
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteOpenHelper
import com.squareup.sqlbrite.SqlBrite
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class ChallengeMappingTest {

    var database: BsDiySQLiteDatabase? = null

    @Before
    fun setUp() {
        val openHelper = BsDiySQLiteOpenHelper.createInMemory(InstrumentationRegistry.getTargetContext())
        val sqlBrite = SqlBrite.Builder().build()
        val briteDatabase = sqlBrite.wrapDatabaseHelper(
                openHelper,
                Schedulers.immediate()
        )
        database = BsDiySQLiteDatabase(briteDatabase)
    }

    @Test
    fun roundTripMappings() {
        val challenge = Challenge(
                id = 12345,
                skillId = 123,
                active = true,
                title = "Dummy Challenge 12345",
                description = "This is a dummy challenge 12345",
                imageIos600Url = "https://example.com/12345/ios600.png",
                imageIos600Mime = "image/png",
                imageIos600Height = 100,
                imageIos600Width = 200,
                position = 0
        )

        database!!.putChallenge(challenge)
        val dbChallenge = database!!.getChallenge(challenge.id).toBlocking().first()

        assertEquals(challenge, dbChallenge)
    }
}
