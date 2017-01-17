package com.bsstokes.bsdiy.db.sqlite.mappers

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteDatabase
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteOpenHelper
import com.squareup.sqlbrite.SqlBrite
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class SkillMappingTest {

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
        val skill = Skill(
                id = 1234,
                active = true,
                url = "https://example.com/1234",
                title = "Dummy Skill 1234",
                description = "This is a dummy skill 1234",
                color = "#BF5700",
                iconSmall = "https://example.com/1234/iconSmall.png",
                iconMedium = "https://example.com/1234/iconMedium.png",
                imageSmall = "https://example.com/1234/iconSmall.png",
                imageMedium = "https://example.com/1234/iconMedium.png",
                imageLarge = "https://example.com/1234/iconLarge.png"
        )

        database!!.putSkill(skill)
        val dbSkill = database!!.getDbSkill(1234).toBlocking().first()

        assertEquals(skill, dbSkill)
    }
}
