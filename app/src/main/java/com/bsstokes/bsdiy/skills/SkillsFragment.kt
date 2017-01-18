package com.bsstokes.bsdiy.skills

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bsstokes.bsdiy.R
import com.bsstokes.bsdiy.app.skill.SkillActivity
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.sync.ApiSyncService
import com.bsstokes.bsdiy.ui.GridDividerDecoration
import com.squareup.picasso.Picasso
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class SkillsFragment : Fragment(), SkillsAdapter.OnClickItemListener {

    companion object {
        const val TAG = "SkillsFragment"

        @JvmStatic
        fun newInstance(): SkillsFragment {
            val fragment = SkillsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @BindView(R.id.skills_list) internal lateinit var skillsListRecyclerView: RecyclerView

    @Inject internal lateinit var database: BsDiyDatabase
    @Inject internal lateinit var picasso: Picasso

    @StringRes private val TITLE = R.string.skills
    @LayoutRes private val LAYOUT = R.layout.fragment_skills

    private val subscriptions = CompositeSubscription()
    private lateinit var skillsAdapter: SkillsAdapter

    private lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(LAYOUT, container, false)
        unbinder = ButterKnife.bind(this, view)

        skillsAdapter = SkillsAdapter(context, picasso, this)
        skillsListRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = skillsAdapter
            addItemDecoration(GridDividerDecoration(context))
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ApiSyncService.syncSkills(activity)
        activity.setTitle(TITLE)

        BsDiyApplication.getApplication(activity).appComponent().inject(this)
        requireNotNull(database)
        requireNotNull(picasso)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    override fun onResume() {
        super.onResume()

        val getAllSkills = database.getAllSkills()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val subscription = getAllSkills
                .subscribe { skills -> skillsAdapter.loadSkills(skills) }

        subscriptions.add(subscription)
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    override fun onClickSkill(skillId: Long) {
        val intent = SkillActivity.createIntent(activity, skillId)
        startActivity(intent)
    }
}
