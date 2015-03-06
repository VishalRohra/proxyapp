package ru.codeninja.proxyapp.header;

import ru.codeninja.proxyapp.url.CurrentUrl;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vital on 07.03.15.
 */
public class Redirect {
    CurrentUrl currentUrl;
    HttpServletResponse response;

    private Redirect(CurrentUrl currentUrl, HttpServletResponse response) {
        this.currentUrl = currentUrl;
        this.response = response;
    }

    public static Redirect location(CurrentUrl currentUrl, HttpServletResponse response) {
        return new Redirect(currentUrl, response);
    }

    public void to(String toUrl) {
        String redirectUrl = currentUrl.encodeUrl(toUrl);
        response.setHeader("Location", redirectUrl);
        response.setStatus(301);
    }


}
