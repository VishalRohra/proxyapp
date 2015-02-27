package ru.codeninja.proxyapp.response;

import ru.codeninja.proxyapp.connection.ProxyConnection;
import ru.codeninja.proxyapp.response.writer.*;

/**
 * Created by vital on 08.02.15.
 */
public class ResponseWriterFactory {
    final static BinaryResponseWriter BINARY_RESPONSE_WRITER = new BinaryResponseWriter();
    final static HtmlResponseWriter HTML_RESPONSE_WRITER = new HtmlResponseWriter();
    final static CssResponseWriter CSS_RESPONSE_WRITER = new CssResponseWriter();
    final static JavascriptResponseWriter JAVASCRIPT_RESPONSE_WRITER = new JavascriptResponseWriter();

    public ResponseWriter get(ProxyConnection connection) {
        String contentType = connection.conn.getContentType();

        if (contentType != null && contentType.contains("text/html")) {
            return HTML_RESPONSE_WRITER;
        } else if (contentType != null && contentType.contains("text/css")) {
            return CSS_RESPONSE_WRITER;
        } else if (contentType != null && contentType.contains("application/x-javascript")) {
            return JAVASCRIPT_RESPONSE_WRITER;
        } else {
            return BINARY_RESPONSE_WRITER;
        }
    }
}
