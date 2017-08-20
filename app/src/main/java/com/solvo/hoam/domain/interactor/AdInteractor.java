package com.solvo.hoam.domain.interactor;

import com.solvo.hoam.data.repository.AdRepository;
import com.solvo.hoam.domain.model.AdEntity;
import com.solvo.hoam.view.detail.DetailViewState;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AdInteractor {

    private AdRepository adRepository;

    @Inject
    public AdInteractor(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public Observable<DetailViewState> getAd(String adId) {
        return adRepository.getAd(adId)
                .subscribeOn(Schedulers.io())
                .map((adEntity -> new DetailViewState.DataState(adEntity)))
                .cast(DetailViewState.class)
                .startWith(new DetailViewState.LoadingState())
                .onErrorReturn((error) -> new DetailViewState.ErrorState(error));
    }

    public Completable setAdFavorite(AdEntity ad, boolean isFavorite) {
        return adRepository.setAdFavorite(ad, isFavorite);
    }

    public Observable<AdEntity> addToFavorites(AdEntity adEntity) {
        return adRepository.addToFavorites(adEntity)
                .subscribeOn(Schedulers.io());
    }

    public Observable<AdEntity> removeFromFavorites(AdEntity adEntity) {
        return adRepository.removeFromFavorites(adEntity)
                .subscribeOn(Schedulers.io());
    }
}
