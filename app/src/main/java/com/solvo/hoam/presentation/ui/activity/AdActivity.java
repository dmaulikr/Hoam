package com.solvo.hoam.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.solvo.hoam.HoamApplication;
import com.solvo.hoam.R;
import com.solvo.hoam.domain.model.AdEntity;
import com.solvo.hoam.presentation.mvp.presenter.AdPresenter;
import com.solvo.hoam.presentation.mvp.view.AdView;
import com.solvo.hoam.presentation.ui.adapter.AdPagerAdapter;
import com.solvo.hoam.presentation.ui.helper.AdHelper;

import me.relex.circleindicator.CircleIndicator;

public class AdActivity extends MvpAppCompatActivity implements AdView {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final String TAG = AdActivity.class.getSimpleName();
    private static final String EXTRA_AD_ID = "ad_id";

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private View errorConnectionView;
    private View contentLayout;
    private View imageLayout;
    private Button tryAgainButton;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView usernameTextView;
    private TextView phoneTextView;
    private TextView categoryTextView;
    private TextView locationTextView;
    private TextView createdDateTextView;
    private TextView descriptionTextView;
    private TextView viewsTextView;
    private CircleIndicator indicator;
    private ViewPager imageViewPager;
    private AdPagerAdapter pagerAdapter;
    private MenuItem favoriteMenuItem;
    private MenuItem unfavoriteMenuItem;

    @InjectPresenter
    AdPresenter presenter;

    @ProvidePresenter
    AdPresenter providePresenter() {
        return new AdPresenter(HoamApplication.getComponent());
    }

    public static Intent buildIntent(Context context, String adId) {
        return new Intent(context, AdActivity.class)
                .putExtra(EXTRA_AD_ID, adId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initToolbar();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        contentLayout = findViewById(R.id.content_layout);
        imageLayout = findViewById(R.id.image_layout);
        errorConnectionView = findViewById(R.id.error_view);
        tryAgainButton = (Button) findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(v -> presenter.onTryAgainClicked());
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        priceTextView = (TextView) findViewById(R.id.tv_price);
        usernameTextView = (TextView) findViewById(R.id.tv_username);
        phoneTextView = (TextView) findViewById(R.id.tv_phone);
        categoryTextView = (TextView) findViewById(R.id.tv_category);
        locationTextView = (TextView) findViewById(R.id.tv_location);
        createdDateTextView = (TextView) findViewById(R.id.tv_created_date);
        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        viewsTextView = (TextView) findViewById(R.id.tv_views);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        imageViewPager = (ViewPager) findViewById(R.id.image_view_pager);

        presenter.init(getIntent().getStringExtra(EXTRA_AD_ID));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.screen_ad));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void inflateMenu() {
        toolbar.inflateMenu(R.menu.menu_ad);
        toolbar.setOnMenuItemClickListener(item -> onMenuItemSelected(item));

        favoriteMenuItem = toolbar.getMenu().findItem(R.id.action_favorite);
        unfavoriteMenuItem = toolbar.getMenu().findItem(R.id.action_favorite);
    }

    private boolean onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_unfavorite:
                presenter.onFavorite(true);
                return true;
            case R.id.action_favorite:
                presenter.onFavorite(false);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setUpViews(AdEntity ad) {
        titleTextView.setText(ad.getTitle());
        priceTextView.setText(AdHelper.getPrice(ad.getPrice()));
        usernameTextView.setText(ad.getAuthorName());
        usernameTextView.setCompoundDrawables(AdHelper.getSupportDrawable(R.drawable.ic_user, this), null, null, null);
        categoryTextView.setText(ad.getCategoryName());
        categoryTextView.setCompoundDrawables(AdHelper.getSupportDrawable(R.drawable.ic_category, this), null, null, null);
        locationTextView.setText(ad.getLocationName());
        locationTextView.setCompoundDrawables(AdHelper.getSupportDrawable(R.drawable.ic_location, this), null, null, null);
        phoneTextView.setText(ad.getPhone());
        phoneTextView.setCompoundDrawables(AdHelper.getSupportDrawable(R.drawable.ic_phone_number, this), null, null, null);
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

    @Override
    public void hideImageLayout() {
        imageLayout.setVisibility(View.GONE);
    }

    @Override
    public void hidePageIndicator() {
        indicator.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            errorConnectionView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        errorConnectionView.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        errorConnectionView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setIsFavorite(boolean isFavorite) {
        favoriteMenuItem.setVisible(!isFavorite);
        unfavoriteMenuItem.setVisible(isFavorite);

        invalidateOptionsMenu();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoriteSuccess(boolean isFavorite) {
        Snackbar.make(coordinatorLayout, isFavorite ? R.string.favorite_added : R.string.favorite_removed, Snackbar.LENGTH_SHORT)
                .show();
    }

}
