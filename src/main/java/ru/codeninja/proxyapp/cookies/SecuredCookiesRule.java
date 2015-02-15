package ru.codeninja.proxyapp.cookies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

/**
 * Created by vital on 14.02.15.
 */
public class SecuredCookiesRule implements CookiesRule {

    static final String COOKIE_HTTP_HEADER = "Cookie";

    @Override
    public void sendCookies(HttpServletRequest request, HttpURLConnection connection) {
        String cookies = request.getHeader(COOKIE_HTTP_HEADER);
        connection.setRequestProperty(COOKIE_HTTP_HEADER, cookies);
    }

    @Override
    public void receiveCookies(HttpServletResponse response, HttpURLConnection cookiesSource) {
        String cookies = cookiesSource.getRequestProperty(COOKIE_HTTP_HEADER);
        response.setHeader(COOKIE_HTTP_HEADER, cookies);
    }
}
