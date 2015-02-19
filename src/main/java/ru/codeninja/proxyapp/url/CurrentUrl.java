package ru.codeninja.proxyapp.url;

import ru.codeninja.proxyapp.cookies.CookiesHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created: 25.01.15 13:35
 *
 * @author Vitaliy Mayorov
 */
public class CurrentUrl {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private URI currentUrl;
    private String baseUrl;
    private String baseHostUrl;
    private boolean hasCookies = false;

    public CurrentUrl(String currentUrl) {
        this(currentUrl, false);
    }

    public CurrentUrl(String currentUrl, boolean isCookiesOn) {
        hasCookies = isCookiesOn;

        try {
            this.currentUrl = new URI(currentUrl);
        } catch (URISyntaxException e) {
            l.log(Level.WARNING, "cannot parse current url", e);
        }

        makeBaseUrl(this.currentUrl);
    }

    private void makeBaseUrl(URI currentUrl) {
        String parentPath = currentUrl.getPath();

        if (!parentPath.endsWith("/")) {
            parentPath = currentUrl.resolve(".").getPath();
        }

        baseUrl = '/' + currentUrl.getAuthority()
                + parentPath;
        baseHostUrl = '/' + currentUrl.getAuthority() + '/';

    }

    private String decodeSpecialChars(String url) {
        return url
                .replaceAll("&#47;", "/")
                .replaceAll("%2F", "/")
                .replaceFirst("%3A", ":");
    }

    private String addCookiesParam(String url) {
        String result = url;
        if (hasCookies && url.contains(baseHostUrl)) {
            StringBuffer buff = new StringBuffer(url);
            if (url.contains("?")) {
                buff.append("&");
                buff.append(CookiesHandler.COOKIES_ON_PARAM);
            } else {
                buff.append("?");
                buff.append(CookiesHandler.COOKIES_ON_PARAM);
            }

            result = buff.toString();
        }

        return result;
    }

    public String encodeUrl(String url) {
        String result = ""; //todo make a default page

        url = decodeSpecialChars(url);

        if (url.startsWith("http://")) {
            result = '/' + url.replaceFirst("http://", "");
        } else if (url.startsWith("//")) {
            result = '/' + url.replaceFirst("//", "");
        } else if (url.startsWith("https://")) {
            result = "/s/" + url.replaceFirst("https://", "");
        } else if (url.startsWith("data:image")) {
            result = url;
        } else if (url.startsWith("/")) {
            result = baseHostUrl + url.replaceFirst("/", "");
        } else {
            result = baseUrl + url;
        }

        //todo url escaping
        return addCookiesParam(result);
    }
}
