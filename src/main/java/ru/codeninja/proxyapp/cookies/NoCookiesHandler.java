package ru.codeninja.proxyapp.cookies;

import ru.codeninja.proxyapp.connection.HttpConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vital on 14.02.15.
 */
public class NoCookiesHandler implements CookiesHandler {
    @Override
    public void sendCookies(HttpServletRequest request, HttpConnection connection) {
        // do nothing
    }

    @Override
    public void receiveCookies(HttpServletResponse response, HttpConnection cookiesSource) {
        // do nothing
    }
}
