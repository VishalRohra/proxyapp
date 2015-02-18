package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.net.HttpURLConnection;

/**
 * Created by vital on 18.02.15.
 */
public class ProxyConnection {
    public HttpURLConnection conn;
    public boolean isCookiesOn = false;

    public ProxyConnection(HttpURLConnection conn, boolean isCookiesOn) {
        this.conn = conn;
        this.isCookiesOn = isCookiesOn;
    }

    public CurrentUrl getCurrentUrl() {
        return new CurrentUrl(conn.getURL().toString(), isCookiesOn);
    }
}
