package ru.codeninja.proxyapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created: 22.01.15 20:47
 *
 * @author Vitaliy Mayorov
 */
public class ProxyServlet extends HttpServlet {

    public static final int READ_BUFFER_SIZE = 1024;
    private UrlConnector urlConnector;

    @Override
    public void init() throws ServletException {
        super.init();

        urlConnector = new UrlConnector();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpURLConnection connection = urlConnector.openGetConnection(req.getRequestURL().toString());
        if (connection == null) {
            //todo implement an error page
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            char[] buff = new char[READ_BUFFER_SIZE];
            int count;
            while ((count = in.read(buff)) != -1) {
                resp.getWriter().write(buff, 0, count);
            }

            resp.setContentType(connection.getContentType());
            resp.setCharacterEncoding(connection.getContentEncoding());
            resp.setStatus(connection.getResponseCode());

            in.close();
        }
    }
}
