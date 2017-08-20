package com.solvo.hoam.view.home;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvi.MviFragment;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.solvo.hoam.R;
import com.solvo.hoam.presentation.ui.adapter.AdListAdapter;
import com.solvo.hoam.presentation.ui.view.EndlessScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class HomeFragment extends MviFragment<HomeView, HomePresenter> implements HomeView {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private EndlessScrollListener scrollListener;
    private LinearLayoutManager layoutManager;
    private AdListAdapter adapter;
    private Unbinder unbinder;

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ad_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        adapter = new AdListAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                presenter.onListScroll(page);
            }
        };

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Observable<Boolean> loadFirstPageIntent() {
        return Observable.just(true).doOnComplete(() -> Log.i(TAG, "firstPage completed"));
    }

    @Override
    public Observable<Boolean> loadNextPageIntent() {
//        return RxRecyclerView.scrollStateChanges(recyclerView).filter()
        return null;
    }

    @Override
    public Observable<Boolean> pullToRefreshIntent() {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .map(ignored -> true);
    }

    @Override
    public void render(HomeViewState viewState) {

    }
}
