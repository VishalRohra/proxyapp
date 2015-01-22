package ru.codeninja.proxyapp;

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

    public HttpURLConnection openGetConnection(String url) {
        HttpURLConnection conn = null;

        try {
            URL urlAddress = new URL(url);
            conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(GET_METHOD);
            conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);

        } catch (IOException e) {
            // ...
        }

        return conn;
    }
}
