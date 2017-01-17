package com.bsstokes.bsdiy.db.sqlite

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapping
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapping
import com.squareup.sqlbrite.SqlBrite
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import rx.schedulers.Schedulers

// Test my implementation of an SqlBrite-backed database. This also tests the mappings between the
// data classes and the SQLite Cursor and ContentValues.
@RunWith(AndroidJUnit4::class)
class BsDiySQLiteDatabaseTest {

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

        briteDatabase.delete(ChallengeMapping.Table.NAME, "1=1")
        briteDatabase.delete(SkillMapping.Table.NAME, "1=1")
    }

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

    val challenge = Challenge(
            id = 12345,
            skillId = skill.id,
            active = true,
            title = "Dummy Challenge 12345",
            description = "This is a dummy challenge 12345",
            imageIos600Url = "https://example.com/12345/ios600.png",
            imageIos600Mime = "image/png",
            imageIos600Height = 100,
            imageIos600Width = 200,
            position = 0
    )

    private val NONEXISTENT_ID: Long = 987654321

    fun <T> Observable<T>.sync(): T {
        return toBlocking().first()
    }

    @Test
    fun an_empty_Skills_table_returns_an_empty_list() {
        assertEquals(emptyList<Skill>(), database!!.getAllSkills().sync())
    }

    @Test
    fun calling_getSkill_with_nonexistent_id_returns_null() {
        assertNull(database!!.getSkill(NONEXISTENT_ID).sync())
    }

    @Test
    fun can_get_a_Skill_by_id() {
        database!!.putSkill(skill)
        assertEquals(skill, database!!.getSkill(skill.id).sync())
    }

    @Test
    fun when_two_Skills_are_inserted_separately_getAllSkills_returns_both() {
        val skill1 = skill.copy(id = 1)
        val skill2 = skill.copy(id = 2)

        database!!.putSkill(skill1)
        database!!.putSkill(skill2)

        assertThat(database!!.getAllSkills().sync(), contains(skill1, skill2))
    }

    @Test
    fun when_two_Skills_are_inserted_together_getAllSkills_returns_both() {
        val skill10 = skill.copy(id = 10)
        val skill20 = skill.copy(id = 20)

        database!!.putSkills(listOf(skill10, skill20))

        assertThat(database!!.getAllSkills().sync(), contains(skill10, skill20))
    }

    @Test
    fun can_get_a_skill_by_url() {
        val skill1 = skill.copy(id = 1, url = "one")
        val skill2 = skill.copy(id = 2, url = "two")

        database!!.putSkills(listOf(skill1, skill2))

        assertEquals(skill1, database!!.getSkillByUrl("one").sync())
        assertEquals(skill2, database!!.getSkillByUrl("two").sync())
    }

    @Test
    fun returns_an_empty_list_when_the_Skill_does_not_exist() {
        assertEquals(emptyList<Challenge>(), database!!.getChallenges(NONEXISTENT_ID).sync())
    }

    @Test
    fun returns_an_empty_list_when_the_Skill_exists_but_has_no_Challenges() {
        database!!.putSkill(skill)
        assertEquals(emptyList<Challenge>(), database!!.getChallenges(skill.id).sync())
    }

    @Test
    fun returns_list_of_Challenges_by_Skill_id_when_inserted_separately() {
        val challenge1 = challenge.copy(id = 1)
        val challenge2 = challenge.copy(id = 2)

        database!!.apply {
            putSkill(skill)
            putChallenge(challenge1)
            putChallenge(challenge2)
        }

        assertThat(database!!.getChallenges(skill.id).sync(), contains(challenge1, challenge2))
    }

    @Test
    fun returns_list_of_Challenges_by_Skill_id_when_inserted_together() {
        val challenge1 = challenge.copy(id = 1)
        val challenge2 = challenge.copy(id = 2)

        database!!.apply {
            putSkill(skill)
            putChallenges(listOf(challenge1, challenge2))
        }

        assertThat(database!!.getChallenges(skill.id).sync(), contains(challenge1, challenge2))
    }

    @Test
    fun returns_Challenge_when_it_exists() {
        database!!.putChallenge(challenge)
        assertEquals(challenge, database!!.getChallenge(challenge.id).sync())
    }

    @Test
    fun returns_null_Challenge_when_it_does_not_exist() {
        assertNull(database!!.getChallenge(NONEXISTENT_ID).sync())
    }
}
