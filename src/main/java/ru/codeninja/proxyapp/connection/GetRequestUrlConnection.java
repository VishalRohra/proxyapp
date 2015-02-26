package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.header.RequestHeadersManager;
import ru.codeninja.proxyapp.request.RequestedUrl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 11.02.15.
 */
public class GetRequestUrlConnection implements UrlConnection {
    final Logger l = Logger.getLogger(this.getClass().getName());

    RequestHeadersManager requestHeadersManager;

    public GetRequestUrlConnection(RequestHeadersManager requestHeadersManager) {
        this.requestHeadersManager = requestHeadersManager;
    }

    @Override
    public ProxyConnection connect(RequestedUrl url) {
        ProxyConnection proxyConnection = null;
        try {
            HttpServletRequest request = url.getRequest();
            URL urlAddress = new URL(url.getUrl());
            HttpURLConnection conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(HttpMethod.GET.getName());

            proxyConnection = new ProxyConnection(conn, url.isCookiesOn());

            requestHeadersManager.sendHeaders(request, proxyConnection);

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return proxyConnection;
    }
}
