package ru.codeninja.proxyapp.cookies;

import ru.codeninja.proxyapp.url.UrlEncoder;

import java.net.HttpCookie;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vital on 16.02.15.
 */
public class CookieProtector {
    public static String neutralize(String path, String setCookieHeader) {
        StringBuffer result = new StringBuffer();

        if (setCookieHeader != null && !setCookieHeader.isEmpty()) {
            List<HttpCookie> httpCookies = HttpCookie.parse(setCookieHeader);
            UrlEncoder urlEncoder = new UrlEncoder(path);
            Iterator<HttpCookie> iterator = httpCookies.iterator();
            while (iterator.hasNext()) {
                HttpCookie cookie = iterator.next();

                String originalPath = cookie.getPath();
                HttpCookie newCookie = new HttpCookie(cookie.getName(), cookie.getValue());
                newCookie.setVersion(1);
                if (originalPath != null) {
                    newCookie.setPath(urlEncoder.encode(originalPath));
                } else {
                    newCookie.setPath(urlEncoder.encode("/"));
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
