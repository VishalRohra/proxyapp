package ru.codeninja.proxyapp.connection;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vital on 11.02.15.
 */
public interface UrlConnection {
    ProxyConnection connect(String url, HttpServletRequest request);
}
