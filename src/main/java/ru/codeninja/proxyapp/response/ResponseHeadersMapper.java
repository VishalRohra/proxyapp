package ru.codeninja.proxyapp.response;

import ru.codeninja.proxyapp.connection.HttpConnection;
import ru.codeninja.proxyapp.url.UrlEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created: 23.01.15 12:01
 *
 * @author Vitaliy Mayorov
 */
public class ResponseHeadersMapper {
    public static final String CSP_POLICY = "default-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data:;";
    private static String[] acceptedHeaders = {"content-type", "expires", "last-modified"};

    public void setHeaders(HttpServletResponse response, HttpConnection headerSource) throws IOException {
        response.setStatus(headerSource.conn.getResponseCode());

        for (String headerName : acceptedHeaders) {
            String value = headerSource.conn.getHeaderField(headerName);
            if (value != null) {
                response.setHeader(headerName, value);
            }
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Security-Policy", CSP_POLICY);
        response.setHeader("X-Content-Security-Policy", CSP_POLICY);

        String redirectLocation = headerSource.conn.getHeaderField("location");
        if (redirectLocation != null) {
            UrlEncoder urlEncoder = new UrlEncoder(headerSource.conn.getURL().getPath());
            response.setHeader("Location", urlEncoder.encode(redirectLocation));
        }
    }
}
