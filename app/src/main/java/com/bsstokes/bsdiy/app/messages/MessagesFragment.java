package com.bsstokes.bsdiy.app.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
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

public class MessagesFragment extends Fragment {

    public static final String TAG = "MessagesFragment";

    @BindView(R.id.text_view) TextView textView;

    @Inject BsDiyDatabase database;
    @Inject Picasso picasso;

    @SuppressWarnings("FieldCanBeLocal")
    private final @StringRes int TITLE = R.string.messages;
    @SuppressWarnings("FieldCanBeLocal") private final @LayoutRes
    int LAYOUT = R.layout.fragment_messages;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private Unbinder unbinder;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {
        final MessagesFragment fragment = new MessagesFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(LAYOUT, container, false);
        unbinder = ButterKnife.bind(this, view);

        textView.setText(R.string.no_messages);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(TITLE);

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
