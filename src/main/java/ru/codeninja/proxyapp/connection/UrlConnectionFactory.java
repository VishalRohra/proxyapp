package ru.codeninja.proxyapp.connection;

/**
 * Created by vital on 11.02.15.
 */
public class UrlConnectionFactory {
    static final GetRequestUrlConnection GET_REQUEST_URL_CONNECTION = new GetRequestUrlConnection();
    static final PostRequestUrlConnection POST_REQUEST_URL_CONNECTION = new PostRequestUrlConnection();

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
