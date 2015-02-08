package ru.codeninja.proxyapp.response;

import ru.codeninja.proxyapp.response.modify.HtmlContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by vital on 08.02.15.
 */
public class HtmlResponseWriter extends AbstractTextResponseWriter {
    final static HtmlContentModifier HTML_CONTENT_MODIFIER = new HtmlContentModifier();

    @Override
    public void sendResponse(HttpURLConnection connection, HttpServletResponse output) throws IOException {
        sendResponse(HTML_CONTENT_MODIFIER, connection, output);
    }

}
