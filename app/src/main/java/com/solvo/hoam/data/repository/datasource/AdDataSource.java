package com.solvo.hoam.data.repository.datasource;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.solvo.hoam.data.db.cursorwrapper.AdCursorWrapper;
import com.solvo.hoam.data.db.model.AdModel;
import com.solvo.hoam.data.db.table.AdTable;
import com.solvo.hoam.view.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AdDataSource {

    private static final String TAG = AdDataSource.class.getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    @Inject
    public AdDataSource(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    private ContentValues buildContentValues(AdModel ad) {
        ContentValues values = new ContentValues();
        values.put(AdTable.ID, ad.getId());
        values.put(AdTable.TITLE, ad.getTitle());
        values.put(AdTable.TEXT, ad.getText());
        values.put(AdTable.PRICE, ad.getPrice());
        values.put(AdTable.PHONE, ad.getPhone());
        values.put(AdTable.VIEWS, ad.getViews());
        values.put(AdTable.AUTHOR_ID, ad.getAuthorId());
        values.put(AdTable.AUTHOR_NAME, ad.getAuthorName());
        values.put(AdTable.CATEGORY_ID, ad.getCategoryId());
        values.put(AdTable.IS_PREMIUM, ad.isPremium() ? 1 : 0);
        values.put(AdTable.CITY_ID, ad.getCityId());
        values.put(AdTable.CREATED_AT, ad.getCreatedAt());
        values.put(AdTable.UPDATED_AT, ad.getUpdatedAt());
        values.put(AdTable.IS_FREE, ad.isFree() ? 1 : 0);
        return values;
    }

    private AdCursorWrapper query(String whereClause, String[] whereArgs) {
        return new AdCursorWrapper(sqLiteDatabase.query(
                AdTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                AdTable.ID + " DESC"
        ));
    }

    public void saveAds(List<AdModel> ads) {
        for (AdModel ad : ads) {
            saveAd(ad);
        }
    }

    public void saveAd(AdModel ad) {
//        sqLiteDatabase.insertWithOnConflict(AdTable.TABLE_NAME, null, buildContentValues(ad), SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.insert(AdTable.TABLE_NAME, null, buildContentValues(ad));
    }

    public void updateAd(AdModel ad) {
        sqLiteDatabase.update(
                AdTable.TABLE_NAME,
                buildContentValues(ad),
                AdTable.ID + " = ?",
                new String[]{ad.getId()});
    }

    public List<AdModel> getAds() {
        List<AdModel> adList = new ArrayList<>();

        AdCursorWrapper cursor = query(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                adList.add(cursor.getAd());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return adList;
    }

    public AdModel getAd(String adId) {
        AdCursorWrapper cursor = query(
                AdTable.ID + " = ?",
                new String[] { adId });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();

            return cursor.getAd();
        } finally {
            cursor.close();
        }
    }

    public void deleteAd(AdModel ad) {
        sqLiteDatabase.delete(AdTable.TABLE_NAME, AdTable.ID + " = ?", new String[]{ad.getId()});
    }
}
