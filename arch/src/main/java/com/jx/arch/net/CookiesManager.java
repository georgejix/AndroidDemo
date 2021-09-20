package com.jx.arch.net;

import com.jx.arch.util.GsonHelper;
import com.jx.arch.util.QMLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar
{
    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies)
    {
        QMLog.i("USER_COOKIE", "HttpUrl: " + url + "\ncookies：" + GsonHelper.toJson(cookies));
        cookieStore.put(url, cookies);
    }

    @Override
    public @NotNull List<Cookie> loadForRequest(HttpUrl url)
    {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<>();
    }
}
