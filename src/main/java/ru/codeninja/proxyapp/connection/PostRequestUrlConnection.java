package ru.codeninja.proxyapp.connection;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by vital on 11.02.15.
 */
public class PostRequestUrlConnection extends AbstractUrlConnection implements UrlConnection {

    @Override
    public HttpURLConnection connect(String url, HttpServletRequest request) {
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
            conn.setRequestMethod(HttpMethod.POST.getName());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postData.length()));

            setBasicHeaders(request, conn);

            OutputStream os = conn.getOutputStream();
            os.write(postData.toString().getBytes());

        } catch (IOException e) {
            l.log(Level.WARNING, e.getMessage(), e);
        }

        return conn;
    }
}
