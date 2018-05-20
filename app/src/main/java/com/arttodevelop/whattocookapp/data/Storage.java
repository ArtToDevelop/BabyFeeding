package com.arttodevelop.whattocookapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arttodevelop.whattocookapp.models.Romka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Storage {

    final String TAG = "Storage";
    final String databaseName = "db";

    Context context;

    DbOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    DatabaseErrorHandler errorHandler;

    public Storage(Context context) {
        this.context = context;

        errorHandler = new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase sqLiteDatabase) {
                Log.d(TAG, "onCorruption");
            }
        };
    }

    public void dbCreate() {

        if(db != null && db.isOpen())
            return;

        if(dbOpenHelper == null)
            dbOpenHelper = new DbOpenHelper(context, databaseName, null, 1, errorHandler);

        db = dbOpenHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    public synchronized int upsertCity(Romka city) {
        if (updateCity(city) == 0)
        {
            // Inserting
            ContentValues contentValues = cityToContentValues(city);
            city.setId((int)db.insertOrThrow(Romka.TableName, null, contentValues));
        }

        Log.d(TAG, "upsertCity: id=" + city.getId());
        return city.getId();
    }



    private synchronized Romka getCity(final String serverId) {
        Cursor cursor = db.query(Romka.TableName, null, "server_id=" + serverId, null, null, null, null);
        cursor.moveToFirst();
        Romka city = getCityFromCursor(cursor);
        cursor.close();

        return city;
    }

    public synchronized void deleteCity(Integer id)
    {
        int delId = db.delete(Romka.TableName, "id = " + id, null);
        Log.d(TAG, "deleteCity: id = " + delId);
    }



    public synchronized List<Romka> getAllCities() {
        Cursor cursor = db.query(Romka.TableName, null, null, null, null, null, "id ASC");
        return getCityListFromCursor(cursor);
    }


    private synchronized long updateCity(Romka item) {
        if (item.getServerId() != null) {
            Romka currentItem = getCity(item.getServerId());

            if (currentItem != null)
                item.setId(currentItem.getId());
        }

        ContentValues contentValues = cityToContentValues(item);

        return db.updateWithOnConflict(Romka.TableName, contentValues, "id = " + item.getId(), null, SQLiteDatabase.CONFLICT_NONE);
    }


    @Nullable
    private synchronized Romka getCityFromCursor(Cursor cursor) {
        if (cursor.isAfterLast())
            return null;

        return cursorToCity(new Romka(), cursor);
    }

    private synchronized Romka cursorToCity(@NonNull Romka city, @NonNull Cursor cursor) {

        city.setId(cursor.getInt(cursor.getColumnIndex("id")));
        city.setServerId(cursor.getString(cursor.getColumnIndex("server_id")));
        city.setName(cursor.getString(cursor.getColumnIndex("name")));

        return city;
    }

    private synchronized ContentValues cityToContentValues(Romka city) {
        ContentValues contentValues = new ContentValues();

        if(city.getId() != null && city.getId() != 0)
            contentValues.put("id", city.getId());

        contentValues.put("server_id", city.getServerId());
        contentValues.put("name", city.getName());

        return contentValues;
    }


    private synchronized List<Romka> getCityListFromCursor(Cursor cursor) {
        List<Romka> result = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Romka city = getCityFromCursor(cursor);
            result.add(city);
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }




}
