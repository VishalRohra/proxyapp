package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.HttpConnection;
import ru.codeninja.proxyapp.response.modify.JavascriptContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vital on 13.02.15.
 */
public class JavascriptResponseWriter extends AbstractTextResponseWriter {
    final static JavascriptContentModifier JAVASCRIPT_CONTENT_MODIFIER = new JavascriptContentModifier();

    @Override
    public void sendResponse(HttpConnection connection, HttpServletResponse output) throws IOException {
        sendResponse(JAVASCRIPT_CONTENT_MODIFIER, connection, output);
    }
}
