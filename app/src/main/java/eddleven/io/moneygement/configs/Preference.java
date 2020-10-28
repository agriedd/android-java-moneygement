package eddleven.io.moneygement.configs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Preference {

    static final String KEY_NAME = "nama_user";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void SET_NAME(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public static boolean isNameSet(Context context){
        return !getSharedPreference(context).getString(KEY_NAME, "").isEmpty();
    }
    public static String getName(Context context){
        return getSharedPreference(context).getString(KEY_NAME, "");
    }
}
