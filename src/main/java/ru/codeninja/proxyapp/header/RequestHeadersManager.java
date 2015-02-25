package ru.codeninja.proxyapp.header;

import ru.codeninja.proxyapp.connection.ProxyConnection;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;

/**
 * Created by vital on 25.02.15.
 */
public class RequestHeadersManager {
    static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    static final String USER_AGENT = "Mozilla/5.0";
    static final String COOKIE_HTTP_HEADER = "Cookie";

    static String[] acceptedHeaders = {"Accept-Language", "Accept", "Accept-Encoding"};

    public void sendHeaders(HttpServletRequest request, ProxyConnection proxyConnection) {
        HttpURLConnection conn = proxyConnection.conn;
        conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);
        for (String headerName : acceptedHeaders) {
            String value = request.getHeader(headerName);
            if (value != null) {
                conn.setRequestProperty(headerName, value);
            }
        }

        if (proxyConnection.isCookiesOn) {
            sendCookies(request, proxyConnection);
        }
    }

    private void sendCookies(HttpServletRequest request, ProxyConnection connection) {
        String cookies = request.getHeader(COOKIE_HTTP_HEADER);
        if (cookies != null) {
            connection.conn.setRequestProperty(COOKIE_HTTP_HEADER, cookies);
        }
    }

}
