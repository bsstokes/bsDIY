package com.bsstokes.bsdiy.app.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bsstokes.bsdiy.R
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.squareup.picasso.Picasso
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

    private lateinit var challengeScreen: ChallengeScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        ButterKnife.bind(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        BsDiyApplication.getApplication(this).appComponent().inject(this)
        requireNotNull(database)
        requireNotNull(picasso)

        intent?.let {
            skillId = intent.getLongExtra(EXTRA_SKILL_ID, skillId)
            challengeId = intent.getLongExtra(EXTRA_CHALLENGE_ID, challengeId)
        }

        challengeScreen = ChallengeScreen(skillId = skillId, challengeId = challengeId, database = database, view = View())
    }

    override fun onResume() {
        super.onResume()
        challengeScreen.start()
    }

    override fun onPause() {
        super.onPause()
        challengeScreen.stop()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class View : ChallengeScreenContract.View {
        override fun setTitle(title: String) {
            this@ChallengeActivity.title = title
        }

        override fun loadPatchImage(patchImageUrl: String) {
            picasso.load(patchImageUrl).into(patchImageView)
        }

        override fun setChallengeTitle(title: String) {
            titleTextView.text = title
        }

        override fun setDescription(description: String) {
            descriptionTextView.text = description
        }
    }
}
