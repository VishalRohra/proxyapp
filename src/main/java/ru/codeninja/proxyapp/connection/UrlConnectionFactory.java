package ru.codeninja.proxyapp.connection;

import ru.codeninja.proxyapp.header.RequestHeadersManager;

/**
 * Created by vital on 11.02.15.
 */
public class UrlConnectionFactory {
    static final RequestHeadersManager REQUEST_HEADERS_MANAGER = new RequestHeadersManager();
    static final GetRequestUrlConnection GET_REQUEST_URL_CONNECTION = new GetRequestUrlConnection(REQUEST_HEADERS_MANAGER);
    static final PostRequestUrlConnection POST_REQUEST_URL_CONNECTION = new PostRequestUrlConnection(REQUEST_HEADERS_MANAGER);

    public UrlConnection get(HttpMethod method) {
        switch (method) {
            case GET:
                return GET_REQUEST_URL_CONNECTION;
            case POST:
                return POST_REQUEST_URL_CONNECTION;
            default:
                throw new UnsupportedOperationException("unsupported http method");
        }
    }
}
