package ru.codeninja.proxyapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

/**
 * Created: 22.01.15 20:47
 *
 * @author Vitaliy Mayorov
 */
public class ProxyServlet extends HttpServlet {
    private final Logger l = Logger.getLogger(this.getClass().getName());

    private UrlConnector urlConnector;
    private RequestParamParser requestParamParser;
    private HeaderMapper headerMapper;

    @Override
    public void init() throws ServletException {
        super.init();

        urlConnector = new UrlConnector();
        requestParamParser = new RequestParamParser();
        headerMapper = new HeaderMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = requestParamParser.getUrl(req);
        HttpURLConnection connection = urlConnector.openGetConnection(url, req);
        if (connection == null) {
            //todo implement an error page
        } else {
            headerMapper.setHeaders(resp, connection);

            ResponseWriter responseWriter = new BinaryResponseWriter();
            responseWriter.sendResponse(connection.getInputStream(), resp);
        }
    }
}
