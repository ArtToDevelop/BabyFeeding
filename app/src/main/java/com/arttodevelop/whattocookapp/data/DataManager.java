package com.arttodevelop.whattocookapp.data;


import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.arttodevelop.BabyFeedingClientApplication;
import com.arttodevelop.whattocookapp.models.Romka;

import java.util.Calendar;
import java.util.List;


public class DataManager {

    private static DataManager instance;

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private Context applicationContext;

    private Storage storage;

    private DataManager() {

        applicationContext = BabyFeedingClientApplication.getContext();

        storage = new Storage(applicationContext);
        storage.dbCreate();
    }
}
