package com.bsstokes.bsdiy.stream;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsstokes.bsdiy.R;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.internal.Preconditions;
import rx.subscriptions.CompositeSubscription;

public class StreamFragment extends Fragment {

    public static final String TAG = "StreamFragment";

    @BindView(R.id.text_view) TextView textView;

    @Inject BsDiyDatabase database;
    @Inject Picasso picasso;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private Unbinder unbinder;

    public StreamFragment() {
        // Required empty public constructor
    }

    public static StreamFragment newInstance() {
        final StreamFragment fragment = new StreamFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stream, container, false);
        unbinder = ButterKnife.bind(this, view);

        textView.setText(getString(R.string.stream));

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
        // Subscribe
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriptions.clear();
    }
}
