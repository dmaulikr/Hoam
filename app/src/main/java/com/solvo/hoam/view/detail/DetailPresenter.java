package com.solvo.hoam.view.detail;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;
import com.solvo.hoam.di.ApplicationComponent;
import com.solvo.hoam.domain.interactor.AdInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailPresenter extends MviBasePresenter<DetailView, DetailViewState> {

    @Inject
    AdInteractor interactor;

    public DetailPresenter(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    protected void bindIntents() {
        intent(detailView -> detailView.addToFavoritesIntent())
                .flatMap(adEntity -> interactor.addToFavorites(adEntity))
                .observeOn(AndroidSchedulers.mainThread());

        intent(detailView -> detailView.removeFromFavoritesIntent())
                .flatMap(adEntity -> interactor.removeFromFavorites(adEntity))
                .observeOn(AndroidSchedulers.mainThread());

        Observable<DetailViewState> loadDetails = intent(detailView -> detailView.loadDetailsIntent())
                .flatMap((adId) -> interactor.getAd(adId))
                .observeOn(AndroidSchedulers.mainThread());

        subscribeViewState(loadDetails, (detailView, state) -> detailView.render(state));
    }
}