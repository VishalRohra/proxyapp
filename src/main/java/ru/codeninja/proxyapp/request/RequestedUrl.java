package ru.codeninja.proxyapp.request;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vital on 25.02.15.
 */
public class RequestedUrl {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    static final Pattern SETTINGS_PATTERN = Pattern.compile("^/([scr]{1,3})/(.*)");

    String url;
    boolean isCookiesOn = false;
    boolean isHttps = false;
    HttpServletRequest request;

    private String decode() {
        String url = request.getPathInfo();
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            url = url + '?' + request.getQueryString();
        }

        String result = null;

        if (url.equals("/") || url.isEmpty()) {
            result = url;
        } else if (SETTINGS_PATTERN.matcher(url).matches()) {
            Matcher m = SETTINGS_PATTERN.matcher(url);
            m.matches();
            String params = m.group(1);
            String path = m.group(2);
            String protocol = params.contains("s") ? "https://" : "http://";

            result = protocol + path;
            isCookiesOn = params.contains("c");
            isHttps = params.contains("s");
        } else if (url.startsWith("/")) {
            result = url.replaceFirst("/", "http://");
        } else {
            l.log(Level.WARNING, "cannot decode url {}", url);
        }

        this.url = result;

        return result;
    }

    @Deprecated
    public RequestedUrl(String url, boolean isCookiesOn, HttpServletRequest request) {
        this.url = url;
        this.isCookiesOn = isCookiesOn;
        this.request = request;
    }

    public static RequestedUrl parse(HttpServletRequest request) {
        RequestedUrl url = new RequestedUrl(request);

        String decodedUrl = url.decode();

        if (decodedUrl == null || decodedUrl.isEmpty() || decodedUrl.equals("/")) {
            url = null;
        }

        return url;
    }

    private RequestedUrl(HttpServletRequest request) {
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

    public boolean isHttps() {
        return isHttps;
    }
}
