package com.solvo.hoam.data.network;

import com.solvo.hoam.data.network.response.Ad;
import com.solvo.hoam.data.network.response.AdResponse;
import com.solvo.hoam.data.network.response.CategoryResponse;
import com.solvo.hoam.data.network.response.LocationResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestService {
    String HOAM_API = "https://hoam.ru/api/";
    String HOAM_URL = "https://hoam.ru/";
    String HOAM_NEW_AD = "https://hoam.ru/publish";

    @GET("category")
    Single<List<CategoryResponse>> getCategories();

    @GET("city")
    Single<List<LocationResponse>> getLocations();

    @GET("ad")
    Single<AdResponse> getAds(
            @Query("page") int page,
            @Query("search") String query,
            @Query("city_id") String locationId,
            @Query("category_id") String categoryId,
            @Query("price_from") String priceFrom,
            @Query("price_to") String priceTo
    );

    @GET("ad/{id}")
    Observable<Ad> getAd(@Path("id") String adId);
}
