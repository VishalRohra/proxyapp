package ru.codeninja.proxyapp.request;

import ru.codeninja.proxyapp.header.Cookies;
import ru.codeninja.proxyapp.url.UrlDecoder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created: 23.01.15 11:33
 *
 * @author Vitaliy Mayorov
 */
@Deprecated
public class RequestParamParser {
    final static UrlDecoder URL_DECODER = new UrlDecoder();

    public RequestedUrl getUrl(HttpServletRequest request) {
        RequestedUrl requestedUrl = null;

        String reqUrl = request.getPathInfo();
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            reqUrl = reqUrl + '?' + request.getQueryString();
        }

        String url = URL_DECODER.decode(reqUrl);

        if (url == null || url.isEmpty() || url.equals("/")) {
            //todo parse path
        } else {
            requestedUrl = new RequestedUrl(url, reqUrl.contains(Cookies.COOKIES_ON_PARAM), request);
        }

        return requestedUrl;
    }
}
