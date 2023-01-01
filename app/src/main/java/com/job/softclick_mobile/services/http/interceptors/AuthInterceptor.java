package com.job.softclick_mobile.services.http.interceptors;

import android.content.Context;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.services.storage.StoredUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        TokenPeer userTokens = StoredUser.load(context);
        if (userTokens == null) {
            return chain.proceed(chain.request());
        }
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + userTokens.getAccessToken());
        Request request = builder.build();

        return chain.proceed(request);
    }
}
