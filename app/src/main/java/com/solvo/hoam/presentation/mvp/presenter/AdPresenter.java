package com.solvo.hoam.presentation.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.solvo.hoam.di.ApplicationComponent;
import com.solvo.hoam.domain.interactor.AdInteractor;
import com.solvo.hoam.domain.model.AdEntity;
import com.solvo.hoam.presentation.mvp.view.AdView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class AdPresenter extends MvpPresenter<AdView> {

    private static final String TAG = AdPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private AdEntity ad;
    private String adId;

    @Inject
    AdInteractor interactor;

    public AdPresenter(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);

        compositeDisposable = new CompositeDisposable();
    }

    public void init(String adId) {
        this.adId = adId;
        getViewState().showLoading(true);
        fetchData();
    }

    public void fetchData() {
//        compositeDisposable.add(interactor.getAd(adId).subscribe(ad -> handleSuccess(ad), throwable -> handleError(throwable)));
    }

    private void handleSuccess(AdEntity ad) {
        this.ad = ad;

        if (ad.getImageList().isEmpty()) {
            getViewState().hideImageLayout();
        } else if (ad.getImageList().size() == 1) {
            getViewState().hidePageIndicator();
        }

        getViewState().setUpViews(ad);
        getViewState().inflateMenu();
        getViewState().setIsFavorite(ad.isFavorite());
        getViewState().showContent();
        getViewState().showLoading(false);
    }

    private void handleError(Throwable throwable) {
        Log.e(TAG, throwable.toString());
        getViewState().showError();
        getViewState().showLoading(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        getViewState().showLoading(false);
    }

    public void onTryAgainClicked() {
        getViewState().showLoading(true);
        fetchData();
    }

    public void onFavorite(boolean isFavorite) {
        ad.setFavorite(isFavorite);
        compositeDisposable.add(interactor.setAdFavorite(ad, isFavorite)
                .subscribe(() -> {
                            getViewState().setIsFavorite(ad.isFavorite());
                            getViewState().showFavoriteSuccess(ad.isFavorite());
                        },
                        throwable -> {
                            Log.e(TAG, throwable.toString());
                        }));
    }


}
