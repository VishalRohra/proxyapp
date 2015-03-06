package ru.codeninja.proxyapp.url;

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
    private String baseUrl;
    private String baseHostUrl;
    private boolean hasCookies = false;
    private String currentUrl;

    public CurrentUrl(String currentUrl) {
        this(currentUrl, false);
    }

    public CurrentUrl(String currentUrl, boolean isCookiesOn) {
        hasCookies = isCookiesOn;
        this.currentUrl = currentUrl;

        makeBaseUrl(currentUrl);
    }

    private void makeBaseUrl(String curUrl) {
        try {
            URI currentUrl = new URI(curUrl);

            String parentPath = currentUrl.getPath();

            if (!parentPath.endsWith("/")) {
                parentPath = currentUrl.resolve(".").getPath();
            }

            baseUrl = currentUrl.getAuthority()
                    + parentPath;
            baseHostUrl = currentUrl.getAuthority() + '/';

        } catch (URISyntaxException e) {
            l.log(Level.WARNING, "cannot parse current url", e);
        }
    }

    private String decodeSpecialChars(String url) {
        return url
                .replaceAll("&#47;", "/")
                .replaceAll("%2F", "/")
                .replaceFirst("%3A", ":");
    }

    private String options(String url, boolean hasCookies) {
        boolean isSslMode = url.startsWith("https://");

        StringBuffer options = new StringBuffer("/");
        if (isSslMode) {
            options.append('s');
        }

        if (hasCookies && (url.contains(baseHostUrl) || currentUrl.equals("/"))) {
            options.append('c');
        }

        if (options.length() > 1) {
            options.append('/');
        }

        return options.toString();
    }

    public String encodeUrl(String url) {
        String result = ""; //todo make a default page

        url = decodeSpecialChars(url);

        if (url.startsWith("http://")) {
            result = options(url, hasCookies) + url.replaceFirst("http://", "");
        } else if (url.startsWith("//")) {
            result = options(url, hasCookies) + url.replaceFirst("//", "");
        } else if (url.startsWith("https://")) {
            result = options(url, hasCookies) + url.replaceFirst("https://", "");
        } else if (url.startsWith("data:image")) {
            result = url;
        } else if (url.startsWith("/")) {
            result = options(currentUrl, hasCookies) + baseHostUrl + url.replaceFirst("/", "");
        } else {
            result = options(currentUrl, hasCookies) + baseUrl + url;
        }

        //todo url escaping
        return result;
    }
}
