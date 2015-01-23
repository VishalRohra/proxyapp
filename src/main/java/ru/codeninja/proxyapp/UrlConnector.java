package ru.codeninja.proxyapp;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created: 22.01.15 21:04
 *
 * @author Vitaliy Mayorov
 */
public class UrlConnector {

    public static final String GET_METHOD = "GET";
    public static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    public static final String USER_AGENT = "Mozilla/5.0";

    public static String[] acceptedHeaders = {"Accept-Language", "Accept"};

    public HttpURLConnection openGetConnection(String url, HttpServletRequest request) {
        HttpURLConnection conn = null;

        try {
            URL urlAddress = new URL(url);
            conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(GET_METHOD);
            conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);
            conn.setRequestProperty("Accept-Encoding", "deflate");

            for (String headerName : acceptedHeaders) {
                String value = request.getHeader(headerName);
                if (value != null) {
                    conn.setRequestProperty(headerName, value);
                }
            }

        } catch (IOException e) {
            // ...
        }

        return conn;
    }
}
