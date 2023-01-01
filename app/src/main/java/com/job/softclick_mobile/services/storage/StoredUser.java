package com.job.softclick_mobile.services.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.job.softclick_mobile.models.TokenPeer;

public class StoredUser {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String USERNAME = "username";

    public static void save(Context context, TokenPeer tokenPeer) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, tokenPeer.getUsername());
        editor.putString(ACCESS_TOKEN, tokenPeer.getAccessToken());
        editor.putString(REFRESH_TOKEN, tokenPeer.getRefreshToken());
        editor.apply();
    }

    public static TokenPeer load(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        TokenPeer tokenPeer;
        String username = sharedPreferences.getString(USERNAME,"");
        String accessToken = sharedPreferences.getString(ACCESS_TOKEN,"");
        String refreshToken = sharedPreferences.getString(REFRESH_TOKEN,"");
        if (username == "" && accessToken == "" && refreshToken == ""){
            tokenPeer = null;
        } else {
            tokenPeer = new TokenPeer();
            tokenPeer.setUsername(username);
            tokenPeer.setAccessToken(accessToken);
            tokenPeer.setRefreshToken(refreshToken);
        }
        return tokenPeer;
    }

    public static void clear(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USERNAME);
        editor.remove(ACCESS_TOKEN);
        editor.remove(REFRESH_TOKEN);
        editor.apply();
    }
}