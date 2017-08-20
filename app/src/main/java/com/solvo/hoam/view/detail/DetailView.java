package com.solvo.hoam.view.detail;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.solvo.hoam.domain.model.AdEntity;

import io.reactivex.Observable;

public interface DetailView extends MvpView {
    Observable<String> loadDetailsIntent();
    Observable<AdEntity> addToFavoritesIntent();
    Observable<AdEntity> removeFromFavoritesIntent();
    void render(DetailViewState state);
}
