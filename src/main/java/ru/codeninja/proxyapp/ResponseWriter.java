package ru.codeninja.proxyapp;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created: 23.01.15 11:50
 *
 * @author Vitaliy Mayorov
 */
public interface ResponseWriter {
    void sendResponse(InputStream input, HttpServletResponse output) throws IOException;
}
