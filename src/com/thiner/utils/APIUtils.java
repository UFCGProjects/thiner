
package com.thiner.utils;

public class APIUtils {

    //    private static final String URL = "http://thiner.herokuapp.com/api/user";
    private static final String URL = "http://192.168.1.244:5000/api/user";

    private static final String URL_LOGIN = "/login";

    public static String getApiUrl() {
        return URL;
    }

    public static String getApiUrlLogin() {
        return getApiUrl() + URL_LOGIN;
    }

    public static String putAttrs(final String key, final String value) {
        return key + "=" + value;
    }
}
