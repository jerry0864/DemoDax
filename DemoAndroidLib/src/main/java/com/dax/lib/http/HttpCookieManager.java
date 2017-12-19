package com.dax.lib.http;/*
 * Copyright (c) 2015 Fran Montiel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dax.lib.common.AppLib;
import com.dax.lib.util.CollectionUtil;
import com.dax.lib.util.Singleton;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class HttpCookieManager implements CookieJar {
    private static final String TAG = "cookieStore";
    private static final String SP_COOKIE_STORE = "ok_http_cookie_store";
    private static final String KEY_ALL_COOKIES = "key_all_cookies";
    private SharedPreferences sharedPreferences;
    private LinkedList<Cookie> mCookieList;
    private Map<Cookie, String> mCookieStringCache;
    private static Singleton<HttpCookieManager> singleton = new Singleton<HttpCookieManager>() {
        @Override
        protected HttpCookieManager init() {
            return new HttpCookieManager(AppLib.getContext());
        }
    };

    public static HttpCookieManager getInstance() {
        return singleton.get();
    }

    private HttpCookieManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_COOKIE_STORE,
                Context.MODE_PRIVATE);
        mCookieList = new LinkedList<>();
        mCookieStringCache = new HashMap<>();
        loadAllFromPersistence();
    }

    private void loadAllFromPersistence() {
        Set<String> allCookies = sharedPreferences.getStringSet(KEY_ALL_COOKIES, new HashSet<String>());
        for (String encodedCookie : allCookies) {
            try {
                Cookie cookie = new SerializableCookie().decode(encodedCookie);
                if (cookie != null) {
                    mCookieList.add(cookie);
                    mCookieStringCache.put(cookie, encodedCookie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clear() {
        synchronized (mCookieList) {
            mCookieList.clear();
            mCookieStringCache.clear();
            removeAllFromPersistence();
        }
    }

    private void removeAllFromPersistence() {
        sharedPreferences.edit().putStringSet(KEY_ALL_COOKIES, new HashSet<String>()).apply();
    }

    private void saveAllToPersistence() {
        if (mCookieList == null) {
            removeAllFromPersistence();
            return;
        }
        Set<String> mCookieSet = new HashSet<>();
        for (Cookie cookie : mCookieList) {
            mCookieSet.add(getEncodeString(cookie));
        }
        sharedPreferences.edit().putStringSet(KEY_ALL_COOKIES, mCookieSet).apply();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (CollectionUtil.isEmpty(cookies)) {
            return;
        }
        synchronized (mCookieList) {
            for (final Cookie newCookie : cookies) {
                CollectionUtil.delete(mCookieList, new CollectionUtil.ItemFilter<Cookie>() {
                    @Override
                    public boolean filter(Cookie cookie) {
                        return TextUtils.equals(cookie.domain(), newCookie.domain()) && TextUtils.equals(cookie.name(), newCookie.name());
                    }
                });
            }
            mCookieList.addAll(cookies);
            saveAllToPersistence();
        }
    }

    private String getEncodeString(Cookie cookie) {
        if (mCookieStringCache.containsKey(cookie)) {
            return mCookieStringCache.get(cookie);
        }
        String encodeString = new SerializableCookie().encode(cookie);
        mCookieStringCache.put(cookie, encodeString);
        return encodeString;
    }

    @Override
    public List<Cookie> loadForRequest(final HttpUrl url) {
        synchronized (mCookieList) {
            return CollectionUtil.filter(mCookieList, new CollectionUtil.ItemFilter<Cookie>() {
                @Override
                public boolean filter(Cookie cookie) {
                    return cookie.matches(url);
                }
            });
        }
    }

    /**
     * same as {@link #loadForRequest(HttpUrl)}
     */
    public List<Cookie> getCookieList(String url) {
        return loadForRequest(HttpUrl.parse(url));
    }

    public String getCookie(String url, String key) {
        List<Cookie> cookieList = loadForRequest(HttpUrl.parse(url));
        if (cookieList != null || cookieList.size() > 0) {
            for (Cookie cookie : cookieList) {
                if (TextUtils.equals(key, cookie.name())) {
                    return cookie.value();
                }
            }
        }
        return null;
    }

    public void removeCookie(String url) {
        try {
            final URL url1 = new URL(url);
            synchronized (mCookieList) {
                CollectionUtil.delete(mCookieList, new CollectionUtil.ItemFilter<Cookie>() {
                    @Override
                    public boolean filter(Cookie cookie) {
                        return TextUtils.equals(cookie.domain(), url1.getHost());
                    }
                });
                saveAllToPersistence();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void copyCookie(String fromUrl, String targetUrl) {
        final HttpUrl httpUrl = HttpUrl.parse(targetUrl);
        List<Cookie> cookies = loadForRequest(HttpUrl.parse(fromUrl));
        if (cookies != null) {
            List<Cookie> newCookieList = CollectionUtil.transfer(cookies, new CollectionUtil.ItemTransfer<Cookie, Cookie>() {
                @Override
                public Cookie transfer(Cookie data) {
                    return new Cookie.Builder().expiresAt(data.expiresAt()).domain(httpUrl.host()).value(data.value()).name(data.name()).build();
                }
            });
            saveFromResponse(httpUrl, newCookieList);
        }
    }
}