package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.ProxyConnection;
import ru.codeninja.proxyapp.response.modify.ContentModifier;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Created by vital on 08.02.15.
 */
public abstract class AbstractTextResponseWriter implements ResponseWriter {
    final Logger l = Logger.getLogger(this.getClass().getName());

    protected void sendResponse(ContentModifier contentModifier, ProxyConnection connection, HttpServletResponse output) throws IOException {
        String contentType = connection.conn.getHeaderField("Content-Type");
        String charset = null;

        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }

        if (charset == null) {
            charset = "utf-8"; //todo hm...
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.conn.getInputStream(), charset));
        output.setCharacterEncoding(charset);
        contentModifier.modify(connection.getCurrentUrl(), reader, output.getWriter());
        reader.close();
    }
}
