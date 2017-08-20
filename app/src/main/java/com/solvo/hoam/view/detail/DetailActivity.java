package com.solvo.hoam.view.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.solvo.hoam.HoamApplication;
import com.solvo.hoam.R;
import com.solvo.hoam.domain.model.AdEntity;
import com.solvo.hoam.presentation.ui.adapter.AdPagerAdapter;
import com.solvo.hoam.presentation.ui.helper.AdHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import me.relex.circleindicator.CircleIndicator;

public class DetailActivity extends MviActivity<DetailView, DetailPresenter> implements DetailView {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final String TAG = DetailActivity.class.getSimpleName();
    private static final String EXTRA_AD_ID = "ad_id";

    private boolean isAdFavorite = false;

    private AdEntity ad;
    private Observable<Boolean> favoriteClickObservable;
    private Observable<Boolean> unfavoriteClickObservable;

    private AdPagerAdapter pagerAdapter;
    private MenuItem favoriteMenuItem;
    private MenuItem unfavoriteMenuItem;

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.error_view) View errorView;
    @BindView(R.id.content_layout) View contentLayout;
    @BindView(R.id.image_layout) View imageLayout;
    @BindView(R.id.try_again_button) Button tryAgainButton;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.tv_title) TextView titleTextView;
    @BindView(R.id.tv_price) TextView priceTextView;
    @BindView(R.id.tv_username) TextView usernameTextView;
    @BindView(R.id.tv_phone) TextView phoneTextView;
    @BindView(R.id.tv_category) TextView categoryTextView;
    @BindView(R.id.tv_location) TextView locationTextView;
    @BindView(R.id.tv_created_date) TextView createdDateTextView;
    @BindView(R.id.tv_description) TextView descriptionTextView;
    @BindView(R.id.tv_views) TextView viewsTextView;
    @BindView(R.id.indicator) CircleIndicator indicator;
    @BindView(R.id.image_view_pager) ViewPager imageViewPager;

    public static void start(Context context, AdEntity adEntity) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_AD_ID, adEntity.getId());
        context.startActivity(intent);
    }

    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter(HoamApplication.getComponent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        initToolbar();
        initMenu();
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.screen_ad));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initMenu() {
        toolbar.inflateMenu(R.menu.menu_ad);

        favoriteMenuItem = toolbar.getMenu().findItem(R.id.action_favorite);
        unfavoriteMenuItem = toolbar.getMenu().findItem(R.id.action_unfavorite);

        favoriteClickObservable = RxMenuItem.clicks(favoriteMenuItem).share().map(ignored -> true);
        unfavoriteClickObservable = RxMenuItem.clicks(unfavoriteMenuItem).share().map(ignored -> true);
    }

    @Override
    public Observable<String> loadDetailsIntent() {
        String adId = getIntent().getStringExtra(EXTRA_AD_ID);
        return Observable.just(adId);
    }

    @Override
    public Observable<AdEntity> addToFavoritesIntent() {
        return unfavoriteClickObservable.filter(item -> ad != null)
                .filter(item -> !isAdFavorite)
                .map(item -> ad);
    }

    @Override
    public Observable<AdEntity> removeFromFavoritesIntent() {
        return favoriteClickObservable.filter(item -> ad != null)
                .filter(item -> isAdFavorite)
                .map(item -> ad);
    }

    @Override
    public void render(DetailViewState state) {
        if (state instanceof DetailViewState.LoadingState) {
            renderLoading();
        } else if (state instanceof DetailViewState.DataState) {
            renderDate((DetailViewState.DataState) state);
        } else if (state instanceof DetailViewState.ErrorState) {
            renderError();
        } else {
            throw new IllegalStateException("Unknown state" + state);
        }
    }

    private void renderError() {
        TransitionManager.beginDelayedTransition(coordinatorLayout);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    private void renderDate(DetailViewState.DataState state) {
        TransitionManager.beginDelayedTransition(coordinatorLayout);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);

        ad = state.getAdEntity();
        isAdFavorite = ad.isFavorite();

        if (ad.getImageList().isEmpty()) {
            imageLayout.setVisibility(View.GONE);
        }

        titleTextView.setText(ad.getTitle());
        priceTextView.setText(AdHelper.getPrice(ad.getPrice()));
        usernameTextView.setText(ad.getAuthorName());
        usernameTextView.setCompoundDrawables(
                AdHelper.getSupportDrawable(R.drawable.ic_user, this),
                null,
                null,
                null
        );
        categoryTextView.setText(ad.getCategoryName());
        categoryTextView.setCompoundDrawables(
                AdHelper.getSupportDrawable(R.drawable.ic_category, this),
                null,
                null,
                null
        );
        locationTextView.setText(ad.getLocationName());
        locationTextView.setCompoundDrawables(
                AdHelper.getSupportDrawable(R.drawable.ic_location, this),
                null,
                null,
                null
        );
        phoneTextView.setText(ad.getPhone());
        phoneTextView.setCompoundDrawables(
                AdHelper.getSupportDrawable(R.drawable.ic_phone_number, this),
                null,
                null,
                null
        );
        createdDateTextView.setText(AdHelper.getAdCreatedDate(ad.getCreatedAt(), false));
        descriptionTextView.setText(ad.getText());
        viewsTextView.setText(AdHelper.getViews(ad.getViews(), getResources()));
        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ad.getPhone())));
        });
        pagerAdapter = new AdPagerAdapter(this, ad.getImageList());
        imageViewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(imageViewPager);
    }

    private void renderLoading() {
        TransitionManager.beginDelayedTransition(coordinatorLayout);
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }
}
