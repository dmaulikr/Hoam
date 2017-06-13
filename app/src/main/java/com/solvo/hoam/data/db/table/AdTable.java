package com.solvo.hoam.data.db.table;

public class AdTable {

    public static final String TABLE_NAME = "ad";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String PRICE = "price";
    public static final String PHONE = "phone";
    public static final String VIEWS = "views";
    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_NAME = "author_name";
    public static final String CATEGORY_ID = "category_id";
    public static final String IS_PREMIUM = "is_premium";
    public static final String CITY_ID = "city_id";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String IS_FREE = "is_free";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER NOT NULL PRIMARY KEY, " +
            TITLE + " TEXT NOT NULL UNIQUE, " +
            TEXT + " TEXT NOT NULL, " +
            PRICE + " INTEGER NOT NULL, " +
            PHONE + " TEXT NOT NULL, " +
            VIEWS + " INTEGER NOT NULL, " +
            AUTHOR_ID + " TEXT NOT NULL, " +
            AUTHOR_NAME + " TEXT NOT NULL, " +
            CATEGORY_ID + " TEXT NOT NULL, " +
            IS_PREMIUM + " INTEGER NOT NULL, " +
            CITY_ID + " TEXT NOT NULL, " +
            CREATED_AT + " TEXT NOT NULL, " +
            UPDATED_AT + " TEXT NOT NULL, " +
            IS_FREE + " INTEGER NOT NULL);";
}
