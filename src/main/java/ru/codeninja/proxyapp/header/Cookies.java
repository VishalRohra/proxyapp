package ru.codeninja.proxyapp.header;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 16.02.15.
 */
public class Cookies {
    final static Logger l = Logger.getLogger(Cookies.class.getName());

    public static String neutralize(CurrentUrl currentUrl, String setCookieHeader) {
        StringBuffer result = new StringBuffer();

        if (setCookieHeader != null && !setCookieHeader.isEmpty()) {
            List<HttpCookie> httpCookies = HttpCookie.parse(setCookieHeader);
            Iterator<HttpCookie> iterator = httpCookies.iterator();
            while (iterator.hasNext()) {
                HttpCookie cookie = iterator.next();

                String originalPath = cookie.getPath();
                HttpCookie newCookie = null;
                try {
                    newCookie = new HttpCookie(
                            URLEncoder.encode(cookie.getName(), "UTF-8"),
                            URLEncoder.encode(cookie.getValue(), "UTF-8")
                    );
                } catch (UnsupportedEncodingException e) {
                    l.log(Level.SEVERE, "cannot parse cookies", e);
                }
                newCookie.setVersion(1);
                if (originalPath != null) {
                    newCookie.setPath(currentUrl.encodeUrl(originalPath));
                } else {
                    newCookie.setPath(currentUrl.encodeUrl("/"));
                }

                newCookie.setSecure(cookie.getSecure());
                result.append(newCookie);

                if (iterator.hasNext()) {
                    result.append(",");
                }
            }
        }

        return result.toString();
    }
}
