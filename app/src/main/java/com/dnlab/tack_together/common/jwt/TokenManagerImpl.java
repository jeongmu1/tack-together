package com.dnlab.tack_together.common.jwt;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManagerImpl implements TokenManager {
    private static final String PREF_NAME = "TackTogether";
    private final String ACCESS_TOKEN_KEY = "AccessToken";
    private final String REFRESH_TOKEN_KEY = "RefreshToken";
    private final SharedPreferences sharedPreferences;

    public TokenManagerImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void storeAccessToken(String accessToken) {
        storeString(ACCESS_TOKEN_KEY, accessToken);
    }

    @Override
    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    @Override
    public void storeRefreshToken(String refreshToken) {
        storeString(REFRESH_TOKEN_KEY, refreshToken);
    }

    @Override
    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    private void storeString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
