package com.solvo.hoam.data.repository.datasource;

import com.solvo.hoam.data.network.RestService;
import com.solvo.hoam.data.network.request.AdRequest;
import com.solvo.hoam.data.network.response.Ad;
import com.solvo.hoam.data.network.response.AdResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ApiAdDataSource {

    private RestService restService;

    @Inject
    public ApiAdDataSource(RestService restService) {
        this.restService = restService;
    }

    public Single<AdResponse> getAds(AdRequest request) {
        return restService.getAds(
                request.getPage(),
                request.getQuery(),
                request.getLocationId(),
                request.getCategoryId(),
                request.getPriceFrom(),
                request.getPriceTo()
        );
    }

    public Observable<Ad> getAd(String adId) {
        return restService.getAd(adId);
    }
}
