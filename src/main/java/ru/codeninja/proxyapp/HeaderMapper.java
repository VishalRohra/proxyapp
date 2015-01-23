package ru.codeninja.proxyapp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created: 23.01.15 12:01
 *
 * @author Vitaliy Mayorov
 */
public class HeaderMapper {
    private static String[] acceptedHeaders = {"content-type", "expires", "last-modified"};

    public void setHeaders(HttpServletResponse response, HttpURLConnection headerSource) throws IOException {
        response.setStatus(headerSource.getResponseCode());

        for (String headerName : acceptedHeaders) {
            String value = headerSource.getHeaderField(headerName);
            if (value != null) {
                response.setHeader(headerName, value);
            }
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
    }
}
