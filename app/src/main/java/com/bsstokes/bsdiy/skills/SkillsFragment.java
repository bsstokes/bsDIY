package com.bsstokes.bsdiy.skills;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsstokes.bsdiy.GridDividerDecoration;
import com.bsstokes.bsdiy.R;
import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.internal.Preconditions;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SkillsFragment extends Fragment {

    public static final String TAG = "SkillsFragment";

    @BindView(R.id.skills_list) RecyclerView skillsListRecyclerView;

    @Inject BsDiyDatabase database;
    @Inject Picasso picasso;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private SkillsAdapter skillsAdapter;

    private Unbinder unbinder;

    public SkillsFragment() {
        // Required empty public constructor
    }

    public static SkillsFragment newInstance() {
        final SkillsFragment fragment = new SkillsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_skills, container, false);
        unbinder = ButterKnife.bind(this, view);

        skillsAdapter = new SkillsAdapter(getContext(), picasso);
        skillsListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        skillsListRecyclerView.setAdapter(skillsAdapter);
        skillsListRecyclerView.addItemDecoration(new GridDividerDecoration(getContext()));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        BsDiyApplication.getApplication(getActivity()).appComponent().inject(this);
        Preconditions.checkNotNull(database);
        Preconditions.checkNotNull(picasso);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        final Observable<List<DiyApi.Skill>> getAllSkills = database.getAllSkills()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        final Subscription subscription = getAllSkills
                .subscribe(new Action1<List<DiyApi.Skill>>() {
                    @Override
                    public void call(List<DiyApi.Skill> skills) {
                        skillsAdapter.loadSkills(skills);
                    }
                });

        subscriptions.add(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriptions.clear();
    }
}