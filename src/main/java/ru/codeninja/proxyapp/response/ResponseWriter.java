package ru.codeninja.proxyapp.response;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created: 23.01.15 11:50
 *
 * @author Vitaliy Mayorov
 */
public interface ResponseWriter {
    void sendResponse(HttpURLConnection connection, HttpServletResponse output) throws IOException;
}
