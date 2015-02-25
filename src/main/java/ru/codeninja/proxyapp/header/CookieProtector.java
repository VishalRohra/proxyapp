package ru.codeninja.proxyapp.header;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.net.HttpCookie;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vital on 16.02.15.
 */
public class CookieProtector {
    public static final String COOKIES_ON_PARAM = "__cookies";

    public static String neutralize(CurrentUrl currentUrl, String setCookieHeader) {
        StringBuffer result = new StringBuffer();

        if (setCookieHeader != null && !setCookieHeader.isEmpty()) {
            List<HttpCookie> httpCookies = HttpCookie.parse(setCookieHeader);
            Iterator<HttpCookie> iterator = httpCookies.iterator();
            while (iterator.hasNext()) {
                HttpCookie cookie = iterator.next();

                String originalPath = cookie.getPath();
                HttpCookie newCookie = new HttpCookie(cookie.getName(), cookie.getValue());
                newCookie.setVersion(1);
                if (originalPath != null) {
                    newCookie.setPath(currentUrl.encodeUrl(originalPath));
                } else {
                    newCookie.setPath(currentUrl.encodeUrl("/"));
                }
                newCookie.setHttpOnly(true);

                result.append(newCookie);

                if (iterator.hasNext()) {
                    result.append(",");
                }
            }
        }

        return result.toString();
    }
}
