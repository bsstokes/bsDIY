package com.bsstokes.bsdiy.db.sqlite.migrations

import android.database.sqlite.SQLiteDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
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

        database.insert("challenges", null,
                Challenge(
                        id = 1000001,
                        active = true,
                        title = "Get Familiar with Him",
                        description = "Who's this Brian guy and what does he want? Put together clues by looking over his resume, website and anything else you can find about him.",
                        imageIos600Url = "https://bsdyi.s3.amazonaws.com/research.jpg",
                        imageIos600Mime = "image/jpeg",
                        imageIos600Width = 600,
                        imageIos600Height = 400,
                        position = 0,
                        skillId = skill.id
                ).toContentValues()
        )

        database.insert("challenges", null,
                Challenge(
                        id = 1000002,
                        active = true,
                        title = "Conduct Some Interviews",
                        description = "Now you're armed with a little bit of information, you're ready to make contact. Let everybody put Brian through the wringer to figure him out.",
                        imageIos600Url = "https://bsdyi.s3.amazonaws.com/interview.jpg",
                        imageIos600Mime = "image/jpeg",
                        imageIos600Width = 600,
                        imageIos600Height = 400,
                        position = 1,
                        skillId = skill.id
                ).toContentValues()
        )

        database.insert("challenges", null,
                Challenge(
                        id = 1000003,
                        active = true,
                        title = "Make an Offer",
                        description = "You love this guy! You just gotta have him on your team. Make him a generous offer, and you'll be on your way to making it a reality.",
                        imageIos600Url = "https://bsdyi.s3.amazonaws.com/offer.jpg",
                        imageIos600Mime = "image/jpeg",
                        imageIos600Width = 600,
                        imageIos600Height = 400,
                        position = 2,
                        skillId = skill.id
                ).toContentValues()
        )

        database.insert("challenges", null,
                Challenge(
                        id = 1000004,
                        active = true,
                        title = "Hire Brian",
                        description = "Everybody's on the same page. We just have to make official. Let's get all the paperwork mumbo jumbo out of the way.",
                        imageIos600Url = "https://bsdyi.s3.amazonaws.com/handshake.jpg",
                        imageIos600Mime = "image/jpeg",
                        imageIos600Width = 600,
                        imageIos600Height = 400,
                        position = 3,
                        skillId = skill.id
                ).toContentValues()
        )

        database.insert("challenges", null,
                Challenge(
                        id = 1000005,
                        active = true,
                        title = "Let's Get to Work!",
                        description = "Congratulations. We have a lot of work to do, and let's get to it!",
                        imageIos600Url = "https://bsdyi.s3.amazonaws.com/work.jpg",
                        imageIos600Mime = "image/jpeg",
                        imageIos600Width = 600,
                        imageIos600Height = 400,
                        position = 4,
                        skillId = skill.id
                ).toContentValues()
        )
    }
}
