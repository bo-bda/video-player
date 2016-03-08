package com.android.bo.video.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.bo.video.BoApplication;
import com.android.bo.video.dreamfactory.DFUser;
import com.google.gson.Gson;

/*
 * Created by Bo on 06.03.2016.
 */
public class Storage {

    private SharedPreferences sharedPreferences;
    private static final String STORAGE_NAME = "STORAGE";
    private static final String USER_PROFILE_KEY = "storage.USER_PROFILE_KEY";

    private Storage() {
        sharedPreferences = BoApplication.getAppContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public static Storage getInstance() {
        return new Storage();
    }

    public void saveUserProfile(DFUser dfUser) {
        Gson gson = new Gson();
        String s = gson.toJson(dfUser);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PROFILE_KEY, s);
        editor.apply();
    }

    public DFUser getUserProfile() {
        Gson gson = new Gson();
        DFUser dfUser = null;
        String s = sharedPreferences.getString(USER_PROFILE_KEY, null);
        if (s != null) {
            dfUser = gson.fromJson(s, DFUser.class);
        }
        return dfUser;
    }
}
