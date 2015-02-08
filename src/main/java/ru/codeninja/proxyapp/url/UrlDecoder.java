package ru.codeninja.proxyapp.url;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created: 25.01.15 14:40
 *
 * @author Vitaliy Mayorov
 */
public class UrlDecoder {
    private final Logger l = Logger.getLogger(this.getClass().getName());

    public String decode(String url) {
        String result = null;
        if (url.startsWith("/s/")) {
            result = url.replaceFirst("/s/", "https://");
        } else if (url.startsWith("/")) {
            result = url.replaceFirst("/", "http://");
        } else {
            l.log(Level.WARNING, "cannot decode url {}", url);
        }

        return UrlEscape.decode(result);
    }
}
