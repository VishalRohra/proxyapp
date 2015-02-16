package ru.codeninja.proxyapp.cookies;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vital on 14.02.15.
 */
public class CookiesHandlerFactory {
    final static NoCookiesHandler NO_COOKIES_HANDLER = new NoCookiesHandler();
    final static SecuredCookiesHandler SECURED_COOKIES_HANDLER = new SecuredCookiesHandler();

    public CookiesHandler getRule(HttpServletRequest request) {
        String cookieParam = request.getParameter("__cookies");
        if (cookieParam == null) {
            return NO_COOKIES_HANDLER;
        } else {
            return SECURED_COOKIES_HANDLER;
        }
    }
}
