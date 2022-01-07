package com.example.kuis;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public class SharedPrefManager {
    private static final Object mLock = new Object();

    private static final String DATA_NAME = "global";

    public static void deleteValue(Context context, @SharedPrefKeyName String keyName) {
        synchronized (mLock) {
            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.remove(keyName);
            editor.apply();
        }
    }

    public static void saveString(Context context, @SharedPrefKeyName String keyName, String value) {
        synchronized (mLock) {
            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(keyName, value);
            editor.apply();
        }
    }

    public static String getSavedString(Context context, @SharedPrefKeyName String keyName) {
        synchronized (mLock) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(keyName)) {
                return sharedPreferences.getString(keyName, null);
            }
            return null;
        }
    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({LOGIN_USERNAME, LOGIN_PASSWORD,LOGIN_SUCCESS})
    public @interface SharedPrefKeyName {
    }

    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_SUCCESS = "loginsuccess";
}
