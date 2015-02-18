package ru.codeninja.proxyapp.connection;

import java.net.HttpURLConnection;

/**
 * Created by vital on 18.02.15.
 */
public class HttpConnection {
    public HttpURLConnection conn;
    public boolean isCookiesOn = false;

    public HttpConnection(HttpURLConnection conn, boolean isCookiesOn) {
        this.conn = conn;
        this.isCookiesOn = isCookiesOn;
    }
}
