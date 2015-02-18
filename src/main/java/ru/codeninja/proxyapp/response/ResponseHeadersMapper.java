package ru.codeninja.proxyapp.response;

import ru.codeninja.proxyapp.url.UrlEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created: 23.01.15 12:01
 *
 * @author Vitaliy Mayorov
 */
public class ResponseHeadersMapper {
    public static final String CSP_POLICY = "default-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data:;";
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
        response.setHeader("Content-Security-Policy", CSP_POLICY);
        response.setHeader("X-Content-Security-Policy", CSP_POLICY);

        String redirectLocation = headerSource.getHeaderField("location");
        if (redirectLocation != null) {
            UrlEncoder urlEncoder = new UrlEncoder(headerSource.getURL().getPath());
            response.setHeader("Location", urlEncoder.encode(redirectLocation));
        }
    }
}
