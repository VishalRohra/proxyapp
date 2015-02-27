package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.header.RequestHeadersManager;
import ru.codeninja.proxyapp.request.RequestedUrl;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 11.02.15.
 */
public class PostRequestUrlConnection implements UrlConnection {
    final Logger l = Logger.getLogger(this.getClass().getName());

    RequestHeadersManager requestHeadersManager;

    public PostRequestUrlConnection(RequestHeadersManager requestHeadersManager) {
        this.requestHeadersManager = requestHeadersManager;
    }

    @Override
    public ProxyConnection connect(RequestedUrl url) {
        ProxyConnection proxyConnection = null;

        try {
            HttpServletRequest request = url.getRequest();

            BufferedReader reader = request.getReader();
            StringBuffer rawData = new StringBuffer();
            char[] buff = new char[1024];
            int count;
            while ((count = reader.read(buff)) != -1) {
                rawData.append(buff, 0, count);
            }

            URL urlAddress = new URL(url.getUrl());
            HttpURLConnection conn = (HttpURLConnection) urlAddress.openConnection();
            conn.setInstanceFollowRedirects(false);

            conn.setDoOutput(true);
            conn.setRequestMethod(HttpMethod.POST.getName());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(rawData.length()));

            proxyConnection = new ProxyConnection(conn, url.isCookiesMode());

            requestHeadersManager.sendHeaders(request, proxyConnection);

            OutputStream os = conn.getOutputStream();
            if (rawData.length() > 0) {
                os.write(rawData.toString().getBytes());
            }


        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return proxyConnection;
    }
}
