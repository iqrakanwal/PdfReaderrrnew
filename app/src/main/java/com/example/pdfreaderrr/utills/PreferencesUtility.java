package com.example.pdfreaderrr.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtility {

    private static final String TOGGLE_ALBUM_GRID = "TOGGLE_ALBUM_GRID" ;
    private static final String PASSWORD = "PASSWORD" ;
    private static final String ANSWER = "ANSWER" ;
    private static final String QUESTION = "QUESTION" ;
    private static final String FIRSTTIME = "FIRSTTIME" ;
    private static final String BACKGROUND_AUDIO = "BACKGROUND_AUDIO" ;
    private static final String SCEENORIENTATITION = "screen_orientation";
    private static final String REPEATMODE  = "repeat_mode";
    private static final String KEY_THEME_SETTING = "key_theme_setting";
    private static PreferencesUtility sInstance;
    private static final String KEY_LAUGH_COUNT = "key_laugh_count";
    private static SharedPreferences mPreferences;

    public PreferencesUtility(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final PreferencesUtility getInstance( Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesUtility(context.getApplicationContext());
        }
        return sInstance;
    }
    public boolean isAlbumsInGrid() {
        return mPreferences.getBoolean(TOGGLE_ALBUM_GRID, true);
    }

    public void setAlbumsInGrid(final boolean b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(TOGGLE_ALBUM_GRID, b);
        editor.apply();
    }

    public boolean isAllowBackgroundAudio() {
        return mPreferences.getBoolean(BACKGROUND_AUDIO, true);
    }

    public void setAllowBackgroundAudio(final boolean b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(BACKGROUND_AUDIO, b);
        editor.apply();
    }
    public  int getScreenOrientation() {
        return mPreferences.getInt(SCEENORIENTATITION, 10);
    }

    public void setScreenOrientation(final int value) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(SCEENORIENTATITION, value);
        editor.apply();
    }

    public  int getRepeatMode() {
        return mPreferences.getInt(REPEATMODE, 10);
    }

    public void setRepeatmode(final int value) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(REPEATMODE, value);
        editor.apply();
    }

    public int getThemeSettings() {
        return mPreferences.getInt(KEY_THEME_SETTING ,0);
    }

    public void setThemSettings(final int i) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(KEY_THEME_SETTING, i);
        editor.apply();
    }
    public int getlaughCount() {
        return mPreferences.getInt(KEY_LAUGH_COUNT ,0);
    }

    public void setLaughCount(final int i) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(KEY_LAUGH_COUNT, i);
        editor.apply();
    }
    public String getPassword() {
        return mPreferences.getString(PASSWORD, null);
    }

    public void setPassword(final String b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PASSWORD, b);
        editor.apply();
    }

    public String getAnswer() {
        return mPreferences.getString(ANSWER, null);
    }

    public void setAnser(final String b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(ANSWER, b);
        editor.apply();
    }

    public String getQuestion() {
        return mPreferences.getString(QUESTION, null);
    }

    public void setQuestion(final String b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(QUESTION, b);
        editor.apply();
    }

  public String getFirsttime() {
        return mPreferences.getString(FIRSTTIME, null);
    }

    public void setFirsttime(final String b) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(FIRSTTIME, b);
        editor.apply();
    }

}
