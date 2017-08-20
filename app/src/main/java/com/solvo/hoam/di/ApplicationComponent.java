package com.solvo.hoam.di;

import android.content.Context;

import com.solvo.hoam.di.module.ContextModule;
import com.solvo.hoam.di.module.DataModule;
import com.solvo.hoam.presentation.mvp.presenter.AdListPresenter;
import com.solvo.hoam.presentation.mvp.presenter.AdPresenter;
import com.solvo.hoam.presentation.mvp.presenter.FavoritesPresenter;
import com.solvo.hoam.presentation.mvp.presenter.FilterPresenter;
import com.solvo.hoam.presentation.mvp.presenter.SplashPresenter;
import com.solvo.hoam.view.detail.DetailPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, DataModule.class})
public interface ApplicationComponent {

    Context getContext();

    void inject(AdPresenter presenter);
    void inject(FilterPresenter presenter);
    void inject(AdListPresenter presenter);
    void inject(SplashPresenter presenter);
    void inject(FavoritesPresenter presenter);
    void inject(DetailPresenter presenter);
}
