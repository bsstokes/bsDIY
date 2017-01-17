package com.bsstokes.bsdiy.db.sqlite.migrations

import android.database.sqlite.SQLiteDatabase
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapper.ChallengeToContentValues.createChallenge
import com.bsstokes.bsdiy.db.sqlite.mappers.toContentValues

class Migration003_InsertDummyData : Migration {

    override fun onUpgrade(database: SQLiteDatabase) {

        val skill = Skill(
                id = 1000000,
                url = "brian",
                title = "Hire a Brian",
                description = "Good people are hard to find. Brian's a good one. Get to know him, and see if you'd like him to be a part of your team. I know you will.",
                active = true,
                imageSmall = "https://bsdyi.s3.amazonaws.com/brian_patch.png",
                imageMedium = "https://bsdyi.s3.amazonaws.com/brian_patch.png",
                imageLarge = "https://bsdyi.s3.amazonaws.com/brian_patch.png",
                iconSmall = "https://d1973c4qjhao9m.cloudfront.net/patches/maker_icon.png",
                iconMedium = "https://d1973c4qjhao9m.cloudfront.net/patches/maker_160x172.png",
                color = "#BF5700"
        )

        val contentValues = skill.toContentValues()
        contentValues.put("priority", 10)
        database.insert("skills", null, contentValues)

        val challenge1 = DiyApi.Challenge()
        challenge1.id = 1000001
        challenge1.active = true
        challenge1.title = "Get Familiar with Him"
        challenge1.description = "Who's this Brian guy and what does he want? Put together clues by looking over his resume, website and anything else you can find about him."
        challenge1.image = DiyApi.Challenge.Image()
        challenge1.image!!.ios_600 = DiyApi.Asset()
        challenge1.image!!.ios_600!!.url = "https://bsdyi.s3.amazonaws.com/research.jpg"
        challenge1.image!!.ios_600!!.mime = "image/jpeg"
        challenge1.image!!.ios_600!!.width = 600
        challenge1.image!!.ios_600!!.height = 400
        database.insert("challenges", null, createChallenge(challenge1, skill.id, 0))

        val challenge2 = DiyApi.Challenge()
        challenge2.id = 1000002
        challenge2.active = true
        challenge2.title = "Conduct Some Interviews"
        challenge2.description = "Now you're armed with a little bit of information, you're ready to make contact. Let everybody put Brian through the wringer to figure him out."
        challenge2.image = DiyApi.Challenge.Image()
        challenge2.image!!.ios_600 = DiyApi.Asset()
        challenge2.image!!.ios_600!!.url = "https://bsdyi.s3.amazonaws.com/interview.jpg"
        challenge2.image!!.ios_600!!.mime = "image/jpeg"
        challenge2.image!!.ios_600!!.width = 600
        challenge2.image!!.ios_600!!.height = 400
        database.insert("challenges", null, createChallenge(challenge2, skill.id, 1))

        val challenge3 = DiyApi.Challenge()
        challenge3.id = 1000003
        challenge3.active = true
        challenge3.title = "Make an Offer"
        challenge3.description = "You love this guy! You just gotta have him on your team. Make him a generous offer, and you'll be on your way to making it a reality."
        challenge3.image = DiyApi.Challenge.Image()
        challenge3.image!!.ios_600 = DiyApi.Asset()
        challenge3.image!!.ios_600!!.url = "https://bsdyi.s3.amazonaws.com/offer.jpg"
        challenge3.image!!.ios_600!!.mime = "image/jpeg"
        challenge3.image!!.ios_600!!.width = 600
        challenge3.image!!.ios_600!!.height = 400
        database.insert("challenges", null, createChallenge(challenge3, skill.id, 2))

        val challenge4 = DiyApi.Challenge()
        challenge4.id = 1000004
        challenge4.active = true
        challenge4.title = "Hire Brian"
        challenge4.description = "Everybody's on the same page. We just have to make official. Let's get all the paperwork mumbo jumbo out of the way."
        challenge4.image = DiyApi.Challenge.Image()
        challenge4.image!!.ios_600 = DiyApi.Asset()
        challenge4.image!!.ios_600!!.url = "https://bsdyi.s3.amazonaws.com/handshake.jpg"
        challenge4.image!!.ios_600!!.mime = "image/jpeg"
        challenge4.image!!.ios_600!!.width = 600
        challenge4.image!!.ios_600!!.height = 400
        database.insert("challenges", null, createChallenge(challenge4, skill.id, 3))

        val challenge5 = DiyApi.Challenge()
        challenge5.id = 1000005
        challenge5.active = true
        challenge5.title = "Let's Get to Work!"
        challenge5.description = "Congratulations. We have a lot of work to do, and let's get to it!"
        challenge5.image = DiyApi.Challenge.Image()
        challenge5.image!!.ios_600 = DiyApi.Asset()
        challenge5.image!!.ios_600!!.url = "https://bsdyi.s3.amazonaws.com/work.jpg"
        challenge5.image!!.ios_600!!.mime = "image/jpeg"
        challenge5.image!!.ios_600!!.width = 600
        challenge5.image!!.ios_600!!.height = 400
        database.insert("challenges", null, createChallenge(challenge5, skill.id, 4))
    }
}
