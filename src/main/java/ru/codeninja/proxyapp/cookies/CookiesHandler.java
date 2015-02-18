package ru.codeninja.proxyapp.cookies;

import ru.codeninja.proxyapp.connection.ProxyConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vital on 14.02.15.
 */
public interface CookiesHandler {
    String COOKIES_ON_PARAM = "__cookies";

    void sendCookies(HttpServletRequest request, ProxyConnection connection);
    void receiveCookies(HttpServletResponse response, ProxyConnection cookiesSource);
}
