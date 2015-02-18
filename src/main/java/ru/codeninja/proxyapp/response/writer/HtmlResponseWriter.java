package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.HttpConnection;
import ru.codeninja.proxyapp.response.modify.HtmlContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vital on 08.02.15.
 */
public class HtmlResponseWriter extends AbstractTextResponseWriter {
    final static HtmlContentModifier HTML_CONTENT_MODIFIER = new HtmlContentModifier();

    @Override
    public void sendResponse(HttpConnection connection, HttpServletResponse output) throws IOException {
        sendResponse(HTML_CONTENT_MODIFIER, connection, output);
    }

}
