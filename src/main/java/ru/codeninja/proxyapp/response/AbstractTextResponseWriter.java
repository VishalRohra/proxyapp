package ru.codeninja.proxyapp.response;

import ru.codeninja.proxyapp.response.modify.ContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 08.02.15.
 */
public abstract class AbstractTextResponseWriter implements ResponseWriter {
    final Logger l = Logger.getLogger(this.getClass().getName());

    protected void sendResponse(ContentModifier contentModifier, HttpURLConnection connection, HttpServletResponse output) throws IOException {
        String contentType = connection.getHeaderField("Content-Type");
        String charset = null;

        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }

        if (charset != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String currentUrl = connection.getURL().toString();
            contentModifier.modify(currentUrl, reader, output.getWriter());
            reader.close();
        } else {
            l.log(Level.WARNING, "cannot determinate charset for content type '{}'", contentType);
        }
    }
}
