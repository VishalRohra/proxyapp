package ru.codeninja.proxyapp.connection;

/**
 * Created by vital on 27.02.15.
 */
public class SpyJsProtector {
    static final String[] SPY_URLS = {
            "google-analytics.com/ga.js",
            ".mail.ru/tracker",
            ".mail.ru/counter",
            ".scorecardresearch.com/p?",
            "tns-counter.ru",
            "counter.yadro.ru"
    };
    public static boolean isSafe(String url) {
        boolean isSafe = true;

        for(String u : SPY_URLS) {
            if (url.contains(u)) {
                isSafe = false;
                break;
            }
        }

        return isSafe;
    }
}
