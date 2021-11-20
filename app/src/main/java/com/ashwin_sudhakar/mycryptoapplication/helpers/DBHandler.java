package com.ashwin_sudhakar.mycryptoapplication.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashwin_sudhakar.mycryptoapplication.model.ModelCoins;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "coinsdb";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "mycoins";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String SYMBOL = "symbol";

    private static final String IMAGE = "image";

    private static final String CURRENT_PRICE = "currentprice";

    private static final String PRICE_CHANGE = "price_change";

    private static final String UPDATED_DATE = "updated_date";

    public DBHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + TABLE_NAME + " ("

                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + NAME + " STRING,"

                + SYMBOL + " STRING,"

                + IMAGE + " STRING,"

                + CURRENT_PRICE + " LONG ,"

                + PRICE_CHANGE + " DOUBLE ,"

                + UPDATED_DATE + " STRING )";


        db.execSQL(query);

    }


    public void addCoinDetails(String name, String symbol, String image, Long currentPrice, Double priceChange, String updateddate) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME, name);

        values.put(SYMBOL, symbol);

        values.put(IMAGE, image);

        values.put(CURRENT_PRICE, currentPrice);

        values.put(PRICE_CHANGE, priceChange);

        values.put(UPDATED_DATE, updateddate);

        db.insert(TABLE_NAME, null, values);

        db.close();

    }



    public ArrayList<ModelCoins> readCoins() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCoins = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<ModelCoins> modelCoins = new ArrayList<>();

        if (cursorCoins.moveToFirst()) {

            do {

                modelCoins.add(new ModelCoins(cursorCoins.getString(1),

                        cursorCoins.getString(2),

                        cursorCoins.getString(3),

                        cursorCoins.getLong(4),

                        cursorCoins.getDouble(5),

                        cursorCoins.getString(6)
                ));

            } while (cursorCoins.moveToNext());


        }

        cursorCoins.close();

        return modelCoins;

    }

    public void updateData(ContentValues contentValues, String whereClause, String[] whereArgs){
        SQLiteDatabase database = getReadableDatabase();
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }
}