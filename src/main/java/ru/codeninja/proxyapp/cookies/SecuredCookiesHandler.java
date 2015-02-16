package ru.codeninja.proxyapp.cookies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by vital on 14.02.15.
 */
public class SecuredCookiesHandler implements CookiesHandler {

    static final String COOKIE_HTTP_HEADER = "Cookie";
    public static final String SET_COOKIE_HTTP_HEADER = "Set-Cookie";

    @Override
    public void sendCookies(HttpServletRequest request, HttpURLConnection connection) {
        String cookies = request.getHeader(COOKIE_HTTP_HEADER);
        if (cookies != null) {
            connection.setRequestProperty(COOKIE_HTTP_HEADER, cookies);
        }
    }

    @Override
    public void receiveCookies(HttpServletResponse response, HttpURLConnection cookiesSource) {
        Map<String, List<String>> requestProperties = cookiesSource.getRequestProperties();
        String currentUrl = cookiesSource.getURL().toString();
        List<String> cookies = requestProperties.get(SET_COOKIE_HTTP_HEADER);
        if (cookies != null) {
            for (String cookie : cookies) {
                response.addHeader(SET_COOKIE_HTTP_HEADER, CookieProtector.neutralize(currentUrl, cookie));
            }
        }

    }
}
