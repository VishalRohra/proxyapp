package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.cookies.CookiesHandler;
import ru.codeninja.proxyapp.request.RequestedUrl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by vital on 11.02.15.
 */
public class GetRequestUrlConnection extends AbstractUrlConnection implements UrlConnection {

    @Override
    public ProxyConnection connect(RequestedUrl url) {
        ProxyConnection proxyConnection = null;
        try {
            HttpServletRequest request = url.getRequest();
            URL urlAddress = new URL(url.getUrl());
            HttpURLConnection conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(HttpMethod.GET.getName());

            setBasicHeaders(request, conn);

            proxyConnection = new ProxyConnection(conn, request.getParameter(CookiesHandler.COOKIES_ON_PARAM) != null);

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return proxyConnection;
    }
}
