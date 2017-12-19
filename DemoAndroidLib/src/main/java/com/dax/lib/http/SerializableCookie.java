package com.dax.lib.http;/*
 * Copyright (c) 2011 James Smith <james@loopj.com>
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

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

import okhttp3.Cookie;

/**
 * Based on the code from this stackoverflow answer http://stackoverflow.com/a/25462286/980387 by janoliver
 * Modifications in the structure of the class and addition of serialization of httpOnly attribute
 */

public class SerializableCookie implements Serializable {
    private static final String TAG = "SerializableHttpCookie";
    private static final String PREFIX_JSON = "new_json_";
    private static final long serialVersionUID = 6374381323722046732L;

    private transient Cookie cookie;

    // Workaround httpOnly: The httpOnly attribute is not accessible so when we
    // serialize and deserialize the cookie it not preserve the same value. We
    // need to access it using reflection
    private Field fieldHttpOnly;

    public SerializableCookie() {

    }

    public String encode(Cookie cookie) {
        this.cookie = cookie;
        return encodeJson();
    }

    public Cookie decode(String encodeCookie) {
        if (encodeCookie != null) {
            Cookie cookie = decodeJson(encodeCookie);
            return cookie;
        }
        return null;
    }

    private String encodeJson() {
        try {
            String result = toJsonString();
            return PREFIX_JSON + byteArrayToHexString(result.getBytes(Charset.forName("utf8")));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException in encodeCookie", e);
        } catch (Exception e) {
            Log.e(TAG, "JSONException in encodeCookie", e);
        }
        return null;
    }

    private Cookie decodeJson(String encodeCookie) {
        try {
            String result = new String(hexStringToByteArray(encodeCookie.replace(PREFIX_JSON, "")), "utf8");
            fromJson(result);
            return cookie;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "JSONException in encodeCookie", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "JSONException in encodeCookie", e);
        }
        return null;
    }

    // Workaround httpOnly (getter)
    private boolean getHttpOnly() {
        try {
            initFieldHttpOnly();
            return (boolean) fieldHttpOnly.get(cookie);
        } catch (Exception e) {
        }
        return false;
    }

    // Workaround httpOnly (setter)
    private void setHttpOnly(boolean httpOnly) {
        try {
            initFieldHttpOnly();
            fieldHttpOnly.set(cookie, httpOnly);
        } catch (Exception e) {
        }
    }

    private void initFieldHttpOnly() throws NoSuchFieldException {
        fieldHttpOnly = cookie.getClass().getDeclaredField("httpOnly");
        fieldHttpOnly.setAccessible(true);
    }

    private String toJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", cookie.name());
        jsonObject.put("value", cookie.value());
        jsonObject.put("domain", cookie.domain());
        jsonObject.put("expiresAt", cookie.expiresAt());
        return jsonObject.toString();
    }

    private void fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getString("name");
        String value = jsonObject.getString("value");
        Cookie.Builder builder = new Cookie.Builder();
        builder.name(name);
        builder.value(value);
        builder.domain(jsonObject.optString("domain", null));
        builder.expiresAt(jsonObject.optLong("expiresAt"));
        this.cookie = builder.build();
    }

    /**
     * Using some super basic byte array &lt;-&gt; hex conversions so we don't
     * have to rely on any large Base64 libraries. Can be overridden if you
     * like!
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    /**
     * Converts hex values from strings to byte array
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}