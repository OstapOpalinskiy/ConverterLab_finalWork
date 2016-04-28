package com.opalinskiy.ostap.converterlab.utils.databaseUtils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opalinskiy.ostap.converterlab.constants.dbConstants;

public class DbHelper extends SQLiteOpenHelper {

    private  final String CREATE_ORGANIZATIONS_TABLE = "create table " +
            dbConstants.TABLE_ORGANIZATIONS + " (" + dbConstants.PRIMARY_KEY_ID + " integer primary key autoincrement, " +
            dbConstants.COLUMN_ID + " text, " +
            dbConstants.COLUMN_OLD_ID + " text, " +
            dbConstants.COLUMN_ORG_TYPE + " text, " +
            dbConstants.COLUMN_TITLE + " text, " +
            dbConstants.COLUMN_REGION_ID + " text, " +
            dbConstants.COLUMN_CITY_ID + " text, " +
            dbConstants.COLUMN_PHONE + " text, " +
            dbConstants.COLUMN_ADDRESS + " text, " +
            dbConstants.COLUMN_LINK + " text, " +
            dbConstants.COLUMN_DATE + " text, " +
            "FOREIGN KEY (" + dbConstants.COLUMN_ORG_TYPE + ")REFERENCES "+ dbConstants.TABLE_ORG_TYPES +
            "("+  dbConstants.PRIMARY_KEY_ID +")" +
            "FOREIGN KEY (" + dbConstants.COLUMN_REGION_ID + ")REFERENCES "+ dbConstants.TABLE_REGIONS +
            "("+ dbConstants.PRIMARY_KEY_ID +")" +
            "FOREIGN KEY (" + dbConstants.COLUMN_ID + ")REFERENCES "+ dbConstants.TABLE_EXCHANGE_RATES +
            "("+ dbConstants.COLUMN_ID_ORGANIZATIONS +")" +
            "FOREIGN KEY (" + dbConstants.COLUMN_CITY_ID + ")REFERENCES "+ dbConstants.TABLE_CITIES +
            "("+ dbConstants.PRIMARY_KEY_ID +")" + ");";


    private static final String CREATE_EXCHANGE_RATE_TABLE = "create table " +
            dbConstants.TABLE_EXCHANGE_RATES + " (" + dbConstants.PRIMARY_KEY_ID + " integer primary key autoincrement, " +
            dbConstants.COLUMN_ID_ORGANIZATIONS + " text, " +
            dbConstants.COLUMN_ID_CURRENCY + " text, " +
            dbConstants.COLUMN_NAME_CURRENCY + " text, " +
            dbConstants.COLUMN_ASK_CURRENCY + " text, " +
            dbConstants.COLUMN_CHANGE_ASK + " text, " +
            dbConstants.COLUMN_BID_CURRENCY + " text, " +
            dbConstants.COLUMN_CHANGE_BID + " text, " +
            "FOREIGN KEY (" + dbConstants.COLUMN_ID_CURRENCY + ")REFERENCES " + dbConstants.TABLE_CURRENCIES + "("
            + dbConstants.PRIMARY_KEY_ID + ")" + ");";

    private static final String CREATE_ORG_TYPES_TABLE = "create table " +
            dbConstants.TABLE_ORG_TYPES + " (" + dbConstants.PRIMARY_KEY_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE, " +
            dbConstants.COLUMN_VALUE + " text);";

    private static final String CREATE_CURRENCIES_TABLE = "create table " +
            dbConstants.TABLE_CURRENCIES + " (" + dbConstants.PRIMARY_KEY_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE, " +
            dbConstants.COLUMN_VALUE + " text);";

    private static final String CREATE_REGIONS_TABLE = "create table " +
            dbConstants.TABLE_REGIONS + " (" + dbConstants.PRIMARY_KEY_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE, " +
            dbConstants.COLUMN_VALUE + " text);";

    private static final String CREATE_CITIES_TABLE = "create table " +
            dbConstants.TABLE_CITIES + " (" + dbConstants.PRIMARY_KEY_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE, " +
            dbConstants.COLUMN_VALUE + " text);";

    public DbHelper(Context context) {
        super(context, dbConstants.DATABASE_NAME, null, dbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ORGANIZATIONS_TABLE);
        db.execSQL(CREATE_CURRENCIES_TABLE);
        db.execSQL(CREATE_EXCHANGE_RATE_TABLE);
        db.execSQL(CREATE_REGIONS_TABLE);
        db.execSQL(CREATE_ORG_TYPES_TABLE);
        db.execSQL(CREATE_CITIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_ORGANIZATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_ORG_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_CURRENCIES);
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_REGIONS);
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_CITIES);
        db.execSQL("DROP TABLE IF EXISTS " + dbConstants.TABLE_EXCHANGE_RATES);
        onCreate(db);
    }
}
