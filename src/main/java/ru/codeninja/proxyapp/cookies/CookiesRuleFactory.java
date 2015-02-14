package ru.codeninja.proxyapp.cookies;

import java.net.HttpURLConnection;

/**
 * Created by vital on 14.02.15.
 */
public class CookiesRuleFactory {
    final static NoCookiesRule NO_COOKIES_RULE = new NoCookiesRule();

    public CookiesRule getCookiesPolice(HttpURLConnection connection) {
        return NO_COOKIES_RULE;
    }
}
