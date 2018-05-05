package com.asmes.meniere.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.asmes.meniere.models.User;


/**
 * Created by Juanma on 28/11/2017.
 */

public class UserSession {

    //User
    public static final String PREFERENCES_NAME = "MENIERE_PREFERENCES";
    public static final String PREFERENCES_EMAIL = "PREFERENCES_EMAIL";
    public static final String PREFERENCES_PASS = "PREFERENCES_PASS";

    private final SharedPreferences mPreferences;
    private boolean mIsLoggedIn = false;

    private static UserSession sInstance;
    private Context mContext;

    public static UserSession getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserSession(context);
        }
        return sInstance;
    }

    private UserSession(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public SharedPreferences getPreferences(){
        return mPreferences;
    }

    public void saveUser(User user) {
        if (user != null) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(PREFERENCES_EMAIL, user.getEmail());
            editor.putString(PREFERENCES_PASS, user.getPass());
            editor.apply();

            mIsLoggedIn = true;
        }
    }
}