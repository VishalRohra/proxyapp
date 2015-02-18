package ru.codeninja.proxyapp.cookies;

import ru.codeninja.proxyapp.connection.ProxyConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by vital on 14.02.15.
 */
public class SecuredCookiesHandler implements CookiesHandler {

    static final String COOKIE_HTTP_HEADER = "Cookie";
    public static final String SET_COOKIE_HTTP_HEADER = "Set-Cookie";

    @Override
    public void sendCookies(HttpServletRequest request, ProxyConnection connection) {
        String cookies = request.getHeader(COOKIE_HTTP_HEADER);
        if (cookies != null) {
            connection.conn.setRequestProperty(COOKIE_HTTP_HEADER, cookies);
        }
    }

    @Override
    public void receiveCookies(HttpServletResponse response, ProxyConnection cookiesSource) {
        Map<String, List<String>> requestProperties = cookiesSource.conn.getHeaderFields();
        List<String> cookies = requestProperties.get(SET_COOKIE_HTTP_HEADER);
        if (cookies != null) {
            for (String cookie : cookies) {
                response.addHeader(SET_COOKIE_HTTP_HEADER, CookieProtector.neutralize(cookiesSource.getCurrentUrl(), cookie));
            }
        }

    }
}
