package com.solvo.hoam.view.home;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface HomeView extends MvpView {
    Observable<Boolean> loadFirstPageIntent();
    Observable<Boolean> loadNextPageIntent();
    Observable<Boolean> pullToRefreshIntent();
    void render(HomeViewState viewState);
}
