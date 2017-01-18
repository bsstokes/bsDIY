package com.bsstokes.bsdiy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.sync.ApiSyncService
import com.squareup.picasso.Picasso
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class SkillActivity : AppCompatActivity() {

    companion object {

        @JvmStatic
        fun createIntent(context: Context, skillId: Long): Intent {
            val intent = Intent(context, SkillActivity::class.java)
            intent.putExtra(EXTRA_SKILL_ID, skillId)
            return intent
        }

        private val EXTRA_SKILL_ID = "EXTRA_SKILL_ID"
    }

    @BindView(R.id.patch_image_view) internal lateinit var patchImageView: ImageView
    @BindView(R.id.description_text_view) internal lateinit var descriptionTextView: TextView
    @BindView(R.id.challenges_layout) internal lateinit var challengesViewGroup: ViewGroup

    @Inject internal lateinit var database: BsDiyDatabase
    @Inject internal lateinit var picasso: Picasso

    private var skillId: Long = 0
    private val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        ButterKnife.bind(this)

        BsDiyApplication.getApplication(this).appComponent().inject(this)
        requireNotNull(database)
        requireNotNull(picasso)

        if (null != intent) {
            skillId = intent.getLongExtra(EXTRA_SKILL_ID, skillId)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        val getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { skill: Skill? -> onLoadSkill(skill) }
        subscriptions.add(getSkill)

        val getChallenges = database.getChallenges(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { challenges: List<Challenge> -> onLoadChallenges(challenges) }
        subscriptions.add(getChallenges)
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun onLoadSkill(skill: Skill?) {
        if (null == skill) {
            Toast.makeText(this, "Couldn't find skill", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }

        skill.url?.let {
            // TODO: "Not the best place to put this because it might be firing too often."
            ApiSyncService.syncChallenges(this, skill.url)
        }

        title = skill.title

        skill.imageLarge?.let {
            val imageUrl = DiyApi.Helper.normalizeUrl(skill.imageLarge)
            picasso.load(imageUrl).into(patchImageView)
        }

        descriptionTextView.text = skill.description
    }

    private fun onLoadChallenges(challenges: List<Challenge>) {
        challengesViewGroup.removeAllViews()
        challenges.forEach {
            onLoadChallenge(it)
        }
    }

    private fun onLoadChallenge(challenge: Challenge?) {
        if (null == challenge) {
            return
        }

        challengesViewGroup.addView(createChallengeView(challenge))
    }

    private fun onClickChallenge(challengeId: Long) {
        startActivity(ChallengeActivity.createIntent(this, skillId, challengeId))
    }

    private fun createChallengeView(challenge: Challenge): View {
        val view = layoutInflater.inflate(R.layout.challenge_list_item_layout, challengesViewGroup, false)
        ButterKnife.findById<TextView>(view, R.id.title_text_view).text = challenge.title

        challenge.imageIos600Url?.let {
            val heroImageView = ButterKnife.findById<ImageView>(view, R.id.hero_image_view)
            picasso.load(challenge.imageIos600Url).into(heroImageView)
        }

        view.setOnClickListener { onClickChallenge(challenge.id) }
        return view
    }
}
