package ru.codeninja.proxyapp.cookies;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vital on 14.02.15.
 */
public class CookiesRuleFactory {
    final static NoCookiesRule NO_COOKIES_RULE = new NoCookiesRule();

    public CookiesRule getCookiesPolice(HttpServletRequest request) {
        return NO_COOKIES_RULE;
    }
}
