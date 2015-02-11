package ru.codeninja.proxyapp.connection;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by vital on 11.02.15.
 */
public class GetRequestUrlConnection extends AbstractUrlConnection implements UrlConnection {
    static final String GET_METHOD = "GET";

    @Override
    public HttpURLConnection connect(String url, HttpServletRequest request) {
        HttpURLConnection conn = null;

        try {
            URL urlAddress = new URL(url);
            conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(GET_METHOD);

            setBasicHeaders(request, conn);

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return conn;
    }
}
