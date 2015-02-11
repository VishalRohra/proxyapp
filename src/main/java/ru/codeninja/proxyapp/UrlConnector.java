package ru.codeninja.proxyapp;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created: 22.01.15 21:04
 *
 * @author Vitaliy Mayorov
 */
public class UrlConnector {
    final Logger l = Logger.getLogger(this.getClass().getName());

    static final String GET_METHOD = "GET";
    static final String POST_METHOD = "POST";
    static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    static final String USER_AGENT = "Mozilla/5.0";

    static String[] acceptedHeaders = {"Accept-Language", "Accept", "Accept-Encoding"};

    public HttpURLConnection openGetConnection(String url, HttpServletRequest request) {
        HttpURLConnection conn = null;

        try {
            URL urlAddress = new URL(url);
            conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setRequestMethod(GET_METHOD);
            conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);

            for (String headerName : acceptedHeaders) {
                String value = request.getHeader(headerName);
                if (value != null) {
                    conn.setRequestProperty(headerName, value);
                }
            }

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return conn;
    }

    public HttpURLConnection openPostConnection(String url, HttpServletRequest request) {
        HttpURLConnection conn = null;

        try {
            StringBuffer postData = new StringBuffer();
            for (String paramName : (List<String>) Collections.list(request.getParameterNames())) {
                if (postData.length() > 0) {
                    postData.append("&");
                }
                postData.append(paramName + "=" + URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
            }
            URL urlAddress = new URL(url);
            conn = (HttpURLConnection) urlAddress.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod(POST_METHOD);
            conn.setRequestProperty(HTTP_HEADER_USER_AGENT, USER_AGENT);

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postData.length()));

            for (String headerName : acceptedHeaders) {
                String value = request.getHeader(headerName);
                if (value != null) {
                    conn.setRequestProperty(headerName, value);
                }
            }

            OutputStream os = conn.getOutputStream();
            os.write(postData.toString().getBytes());

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return conn;
    }
}
