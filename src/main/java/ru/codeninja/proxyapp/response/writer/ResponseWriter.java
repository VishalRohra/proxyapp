package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.HttpConnection;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created: 23.01.15 11:50
 *
 * @author Vitaliy Mayorov
 */
public interface ResponseWriter {
    void sendResponse(HttpConnection connection, HttpServletResponse output) throws IOException;
}
