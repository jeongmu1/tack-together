package com.dnlab.tack_together.common.jwt;

import android.content.Context;

public interface TokenManager {
    void storeAccessToken(String accessToken);
    String getAccessToken();
    void storeRefreshToken(String refreshToken);
    String getRefreshToken();
}
