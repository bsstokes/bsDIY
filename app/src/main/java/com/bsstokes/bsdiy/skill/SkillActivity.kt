package com.bsstokes.bsdiy.skill

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
import com.bsstokes.bsdiy.ChallengeActivity
import com.bsstokes.bsdiy.R
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.sync.ApiSyncService
import com.squareup.picasso.Picasso
import javax.inject.Inject

class SkillActivity : AppCompatActivity(), SkillScreenContract.View {

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
    private lateinit var skillScreen: SkillScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        ButterKnife.bind(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        BsDiyApplication.getApplication(this).appComponent().inject(this)
        requireNotNull(database)
        requireNotNull(picasso)

        if (null != intent) {
            skillId = intent.getLongExtra(EXTRA_SKILL_ID, skillId)
        }

        skillScreen = SkillScreen(skillId, database, this)
    }

    override fun onResume() {
        super.onResume()
        skillScreen.start()
    }

    override fun onPause() {
        super.onPause()
        skillScreen.stop()
    }

    override fun showErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun setTitle(title: String) {
        super.setTitle(title)
    }

    override fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    override fun loadPatchImage(patchImageUrl: String) {
        picasso.load(patchImageUrl).into(patchImageView)
    }

    override fun clearChallengeViews() {
        challengesViewGroup.removeAllViews();
    }

    override fun loadChallengeView(challengeId: Long, title: String, heroImageUrl: String?) {
        challengesViewGroup.addView(createChallengeView(challengeId, title, heroImageUrl))
    }

    private fun createChallengeView(challengeId: Long, title: String, heroImageUrl: String?): View {
        val view = layoutInflater.inflate(R.layout.challenge_list_item_layout, challengesViewGroup, false)

        ButterKnife.findById<TextView>(view, R.id.title_text_view).text = title

        heroImageUrl?.let {
            val heroImageView = ButterKnife.findById<ImageView>(view, R.id.hero_image_view)
            picasso.load(heroImageUrl).into(heroImageView)
        }

        view.setOnClickListener { skillScreen.onClickChallenge(challengeId) }
        return view
    }

    override fun launchChallengeActivity(skillId: Long, challengeId: Long) {
        startActivity(ChallengeActivity.createIntent(this, skillId, challengeId))
    }

    override fun startSyncChallengesService(skillUrl: String) {
        ApiSyncService.syncChallenges(this, skillUrl)
    }
}
