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
        String url = URL_DECODER.decode(request.getPathInfo());

        if (url == null || url.isEmpty()) {
            //todo parse path
        }

        return url;
    }
}
