package com.solvo.hoam.data.db.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.solvo.hoam.data.db.model.AdModel;
import com.solvo.hoam.data.db.table.AdTable;

public class AdCursorWrapper extends CursorWrapper {

    public AdCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AdModel getAd() {
        return new AdModel(
                getString(getColumnIndex(AdTable.ID)),
                getString(getColumnIndex(AdTable.TITLE)),
                getString(getColumnIndex(AdTable.TEXT)),
                getLong(getColumnIndex(AdTable.PRICE)),
                getString(getColumnIndex(AdTable.PHONE)),
                getInt(getColumnIndex(AdTable.VIEWS)),
                getString(getColumnIndex(AdTable.AUTHOR_ID)),
                getString(getColumnIndex(AdTable.AUTHOR_NAME)),
                getString(getColumnIndex(AdTable.CATEGORY_ID)),
                getInt(getColumnIndex(AdTable.IS_PREMIUM)) != 0,
                getString(getColumnIndex(AdTable.CITY_ID)),
                getString(getColumnIndex(AdTable.CREATED_AT)),
                getString(getColumnIndex(AdTable.UPDATED_AT)),
                getInt(getColumnIndex(AdTable.IS_PREMIUM)) != 0
        );
    }
}
