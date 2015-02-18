package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.HttpConnection;
import ru.codeninja.proxyapp.response.modify.CssContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vital on 08.02.15.
 */
public class CssResponseWriter extends AbstractTextResponseWriter {
    final static CssContentModifier CSS_CONTENT_MODIFIER = new CssContentModifier();

    @Override
    public void sendResponse(HttpConnection connection, HttpServletResponse output) throws IOException {
        sendResponse(CSS_CONTENT_MODIFIER, connection, output);
    }
}
