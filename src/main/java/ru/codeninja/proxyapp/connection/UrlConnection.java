package ru.codeninja.proxyapp.connection;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;

/**
 * Created by vital on 11.02.15.
 */
public interface UrlConnection {
    HttpURLConnection connect(String url, HttpServletRequest request);
}
