package com.bsstokes.bsdiy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.ui.ActionBarHelper
import com.squareup.picasso.Picasso
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ChallengeActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun createIntent(context: Context, skillId: Long, challengeId: Long): Intent {
            val intent = Intent(context, ChallengeActivity::class.java)
            intent.putExtra(EXTRA_SKILL_ID, skillId)
            intent.putExtra(EXTRA_CHALLENGE_ID, challengeId)
            return intent
        }

        private val EXTRA_SKILL_ID = "EXTRA_SKILL_ID"
        private val EXTRA_CHALLENGE_ID = "EXTRA_CHALLENGE_ID"
    }

    @BindView(R.id.patch_image_view) internal lateinit var patchImageView: ImageView
    @BindView(R.id.title_text_view) internal lateinit var titleTextView: TextView
    @BindView(R.id.description_text_view) internal lateinit var descriptionTextView: TextView

    @Inject internal lateinit var database: BsDiyDatabase
    @Inject internal lateinit var picasso: Picasso

    private var skillId: Long = 0
    private var challengeId: Long = 0
    private val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        ButterKnife.bind(this)

        BsDiyApplication.getApplication(this).appComponent().inject(this)
        requireNotNull(database)
        requireNotNull(picasso)

        if (null != intent) {
            skillId = intent.getLongExtra(EXTRA_SKILL_ID, skillId)
            challengeId = intent.getLongExtra(EXTRA_CHALLENGE_ID, challengeId)
        }

        ActionBarHelper.setDisplayShowHomeEnabled(this)
    }

    override fun onResume() {
        super.onResume()

        val getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { skill -> onLoadSkill(skill) }
        subscriptions.add(getSkill)

        val getChallenge = database.getChallenge(challengeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { challenge -> onLoadChallenge(challenge) }
        subscriptions.add(getChallenge)
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun onLoadSkill(skill: Skill?) {
        skill?.iconMedium?.let {
            picasso.load(DiyApi.Helper.normalizeUrl(skill.iconMedium)).into(patchImageView)
        }
    }

    private fun onLoadChallenge(challenge: Challenge?) {
        title = ""
        challenge?.let {
            titleTextView.text = challenge.title
            descriptionTextView.text = challenge.description
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
