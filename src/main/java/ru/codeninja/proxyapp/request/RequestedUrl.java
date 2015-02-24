package ru.codeninja.proxyapp.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vital on 25.02.15.
 */
public class RequestedUrl {
    String url;
    boolean isCookiesOn = false;
    HttpServletRequest request;

    public RequestedUrl(String url, boolean isCookiesOn, HttpServletRequest request) {
        this.url = url;
        this.isCookiesOn = isCookiesOn;
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public boolean isCookiesOn() {
        return isCookiesOn;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
