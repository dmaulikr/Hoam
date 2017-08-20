package com.solvo.hoam.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.solvo.hoam.HoamApplication;
import com.solvo.hoam.R;
import com.solvo.hoam.presentation.mvp.presenter.SplashPresenter;
import com.solvo.hoam.presentation.mvp.view.SplashView;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    private View errorConnectionView;
    private Button tryAgainButton;

    @InjectPresenter
    SplashPresenter presenter;

    @ProvidePresenter
    SplashPresenter providePresenter() {
        return new SplashPresenter(HoamApplication.getComponent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        errorConnectionView = findViewById(R.id.error_view);
        tryAgainButton = (Button) findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(v -> presenter.onTryAgainClicked());

        presenter.fetchData();
    }

    @Override
    public void showError(boolean show) {
        errorConnectionView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(MainActivity.buildIntent(this));
    }
}
