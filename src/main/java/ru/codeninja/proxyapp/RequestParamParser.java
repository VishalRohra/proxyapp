package ru.codeninja.proxyapp;

import ru.codeninja.proxyapp.url.UrlDecoder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created: 23.01.15 11:33
 *
 * @author Vitaliy Mayorov
 */
public class RequestParamParser {
    private static final String URL_PARAM = "url";
    final static UrlDecoder URL_DECODER = new UrlDecoder();

    public String getUrl(HttpServletRequest request) {
        String requestedUrl = request.getPathInfo();
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            requestedUrl = requestedUrl + '?' + request.getQueryString();
        }

        String url = URL_DECODER.decode(requestedUrl);

        if (url == null || url.isEmpty()) {
            //todo parse path
        }

        return url;
    }
}
