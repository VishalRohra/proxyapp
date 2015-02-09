package ru.codeninja.proxyapp.url;

/**
 * Created by vital on 08.02.15.
 */
@Deprecated
public class UrlEscape {

    public static String encode(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }

        StringBuffer result = new StringBuffer();

        for (char ch : url.toCharArray()) {
            switch (ch) {
                //todo hm...
                case ':':
                    result.append("%3A");
                    break;
                default:
                    result.append(ch);

            }
        }

        return result.toString();
    }

    public static String decode(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }

        return url.replace("%3A", ":");
    }
}
