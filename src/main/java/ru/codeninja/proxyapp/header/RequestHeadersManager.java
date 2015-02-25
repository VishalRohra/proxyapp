package ru.codeninja.proxyapp.header;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;

/**
 * Created by vital on 25.02.15.
 */
public class RequestHeadersManager {
    protected static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    protected static final String USER_AGENT = "Mozilla/5.0";

    protected static String[] acceptedHeaders = {"Accept-Language", "Accept", "Accept-Encoding"};

    public void setBasicHeaders(HttpServletRequest request, HttpURLConnection conn) {
        conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);
        for (String headerName : acceptedHeaders) {
            String value = request.getHeader(headerName);
            if (value != null) {
                conn.setRequestProperty(headerName, value);
            }
        }
    }
}
