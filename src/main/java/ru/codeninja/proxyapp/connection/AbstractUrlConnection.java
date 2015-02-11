package ru.codeninja.proxyapp.connection;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

/**
 * Created: 22.01.15 21:04
 *
 * @author Vitaliy Mayorov
 */
abstract public class AbstractUrlConnection {
    final Logger l = Logger.getLogger(this.getClass().getName());

    protected static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    protected static final String USER_AGENT = "Mozilla/5.0";

    protected static String[] acceptedHeaders = {"Accept-Language", "Accept", "Accept-Encoding"};

    protected void setBasicHeaders(HttpServletRequest request, HttpURLConnection conn) {
        conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);
        for (String headerName : acceptedHeaders) {
            String value = request.getHeader(headerName);
            if (value != null) {
                conn.setRequestProperty(headerName, value);
            }
        }
    }
}
