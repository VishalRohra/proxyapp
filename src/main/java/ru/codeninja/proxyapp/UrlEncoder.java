package ru.codeninja.proxyapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright 2009-2012. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 25.01.15 13:35
 *
 * @author Vitaliy Mayorov
 */
public class UrlEncoder {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private URI currentUrl;
    private String baseUrl;
    private String baseHostUrl;

    public UrlEncoder(String currentUrl) {
        try {
            this.currentUrl = new URI(currentUrl);
        } catch (URISyntaxException e) {
            l.log(Level.WARNING, "cannot parse current url", e);
        }

        makeBaseUrl(this.currentUrl);
    }

    private void makeBaseUrl(URI currentUrl) {
        //todo port
        String parentPath = currentUrl.getPath();
        if (!parentPath.endsWith("/")) {
            parentPath = currentUrl.resolve(".").getPath();
        }

        baseUrl = '/' + currentUrl.getAuthority()
                + parentPath;
        baseHostUrl = '/' + currentUrl.getAuthority() + '/';

    }

    public String encode(String url) {
        String result = ""; //todo make a default page

        if (url.startsWith("http://")) {
            result = '/' + url.replaceFirst("http://", "");
        } else if (url.startsWith("//")) {
            result = '/' + url.replaceFirst("//", "");
        } else if (url.startsWith("https://")) {
            result = "/s/" + url.replaceFirst("https://", "");
        } else if (url.startsWith("/")) {
            result = baseHostUrl + url.replaceFirst("/", "");
        } else {
            result = baseUrl + url;
        }

        return result;
    }
}
