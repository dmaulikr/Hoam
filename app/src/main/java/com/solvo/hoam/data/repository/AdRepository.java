package com.solvo.hoam.data.repository;

import com.solvo.hoam.data.db.model.AdModel;
import com.solvo.hoam.data.db.model.CategoryModel;
import com.solvo.hoam.data.db.model.ImageModel;
import com.solvo.hoam.data.db.model.LocationModel;
import com.solvo.hoam.data.mapper.AdModelEntityMapper;
import com.solvo.hoam.data.mapper.AdResponseEntityMapper;
import com.solvo.hoam.data.mapper.ImageModelEntityMapper;
import com.solvo.hoam.data.network.request.AdRequest;
import com.solvo.hoam.data.network.response.Ad;
import com.solvo.hoam.data.network.response.Image;
import com.solvo.hoam.data.repository.datasource.AdDataSource;
import com.solvo.hoam.data.repository.datasource.ApiAdDataSource;
import com.solvo.hoam.data.repository.datasource.CategoryDataSource;
import com.solvo.hoam.data.repository.datasource.ImageDataSource;
import com.solvo.hoam.data.repository.datasource.LocationDataSource;
import com.solvo.hoam.domain.model.AdEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class AdRepository {

    private ApiAdDataSource apiAdDataSource;
    private AdDataSource adDataSource;
    private LocationDataSource locationDataSource;
    private CategoryDataSource categoryDataSource;
    private ImageDataSource imageDataSource;
    private AdResponseEntityMapper adResponseEntityMapper;
    private AdModelEntityMapper adModelEntityMapper;
    private ImageModelEntityMapper imageModelEntityMapper;

    @Inject
    public AdRepository(ApiAdDataSource apiAdDataSource,
                        AdDataSource adDataSource,
                        LocationDataSource locationDataSource,
                        CategoryDataSource categoryDataSource,
                        ImageDataSource imageDataSource,
                        AdResponseEntityMapper adResponseEntityMapper,
                        AdModelEntityMapper adModelEntityMapper,
                        ImageModelEntityMapper imageModelEntityMapper) {
        this.apiAdDataSource = apiAdDataSource;
        this.adDataSource = adDataSource;
        this.locationDataSource = locationDataSource;
        this.categoryDataSource = categoryDataSource;
        this.imageDataSource = imageDataSource;
        this.adResponseEntityMapper = adResponseEntityMapper;
        this.adModelEntityMapper = adModelEntityMapper;
        this.imageModelEntityMapper = imageModelEntityMapper;
    }

    private AdEntity buildAdEntity(Ad ad, boolean isFavorite) {
        AdEntity adEntity = adResponseEntityMapper.map(ad);

        String locationId = ad.getCityId();
        if (locationId != null) {
            LocationModel locationModel = locationDataSource.getLocationById(locationId);
            adEntity.setLocationName(locationModel.getName());
        }

        String categoryId = ad.getCategoryId();
        if (categoryId != null) {
            CategoryModel categoryModel = categoryDataSource.getCategoryById(categoryId);
            adEntity.setCategoryName(categoryModel.getName());
        }

        adEntity.setFavorite(isFavorite);
        return adEntity;
    }

    public Single<List<AdEntity>> getAds(AdRequest request) {
        return apiAdDataSource.getAds(request)
                .map(adResponse -> adResponse.getAdList())
                .toObservable()
                .flatMap(ads -> Observable.fromIterable(ads))
                .map(ad -> buildAdEntity(ad, false))
                .toList();

    }

    public Observable<AdEntity> getAd(String adId) {
        return apiAdDataSource.getAd(adId)
                .map(ad -> {
                    AdModel adModel = adDataSource.getAd(adId);
                    return adModel != null ? buildAdEntity(ad, true) : buildAdEntity(ad, false);
                });
    }

    public Single<List<AdEntity>> getFavoriteAds() {
        return Single.create((SingleOnSubscribe<List<AdModel>>) e -> e.onSuccess(adDataSource.getAds()))
                .toObservable()
                .flatMap(ads -> Observable.fromIterable(ads))
                .map(ad -> {
                    List<ImageModel> imageModelList = imageDataSource.getImagesByAdId(ad.getId());
                    List<Image> imageList = imageModelEntityMapper.direct(imageModelList);
                    AdEntity adEntity = adModelEntityMapper.direct(ad, imageList);

                    String cityId = ad.getCityId();
                    if (!cityId.equals("null")) {
                        LocationModel locationModel = locationDataSource.getLocationById(cityId);
                        adEntity.setLocationName(locationModel.getName());
                    }

                    String categoryId = ad.getCategoryId();
                    if (!categoryId.equals("null")) {
                        CategoryModel categoryModel = categoryDataSource.getCategoryById(categoryId);
                        adEntity.setCategoryName(categoryModel.getName());
                    }

                    return adEntity;
                })
                .toList();
    }

    public Observable<AdEntity> addToFavorites(AdEntity adEntity) {
        return Observable.just(adEntity)
                .map(ad -> {
                    List<ImageModel> imageList = imageModelEntityMapper.indirect(ad.getImageList());
                    AdModel adModel = adModelEntityMapper.indirect(ad);
                    imageDataSource.saveImages(imageList);
                    adDataSource.saveAd(adModel);

                    ad.setFavorite(true);
                    return ad;
                });
    }

    public Observable<AdEntity> removeFromFavorites(AdEntity adEntity) {
        return Observable.just(adEntity)
                .map(ad -> {
                    List<ImageModel> imageList = imageModelEntityMapper.indirect(ad.getImageList());
                    AdModel adModel = adModelEntityMapper.indirect(ad);
                    imageDataSource.deleteImages(imageList);
                    adDataSource.deleteAd(adModel);

                    ad.setFavorite(false);
                    return ad;
                });
    }

    public Completable setAdFavorite(AdEntity ad, boolean isFavorite) {
        return Completable.create(e -> {
            List<ImageModel> imageList = imageModelEntityMapper.indirect(ad.getImageList());
            AdModel adModel = adModelEntityMapper.indirect(ad);

            if (isFavorite) {
                imageDataSource.saveImages(imageList);
                adDataSource.saveAd(adModel);
            } else {
                imageDataSource.deleteImages(imageList);
                adDataSource.deleteAd(adModel);
            }
            e.onComplete();
        });
    }
}
