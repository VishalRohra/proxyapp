package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.ProxyConnection;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created: 23.01.15 11:50
 *
 * @author Vitaliy Mayorov
 */
public interface ResponseWriter {
    void sendResponse(ProxyConnection connection, HttpServletResponse output) throws IOException;
}
