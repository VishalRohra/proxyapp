package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.request.RequestedUrl;

/**
 * Created by vital on 11.02.15.
 */
public interface UrlConnection {
    ProxyConnection connect(RequestedUrl url);
}
