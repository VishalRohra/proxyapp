package ru.codeninja.proxyapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created: 23.01.15 11:33
 *
 * @author Vitaliy Mayorov
 */
public class RequestParamParser {
    private static final String URL_PARAM = "url";

    public String getUrl(HttpServletRequest request) {
        String url = request.getParameter(URL_PARAM);

        if (url == null || url.isEmpty()) {
            //todo parse path
        }

        return url;
    }
}
