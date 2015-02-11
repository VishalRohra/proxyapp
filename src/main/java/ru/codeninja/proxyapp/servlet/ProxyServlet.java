package ru.codeninja.proxyapp.servlet;

import ru.codeninja.proxyapp.HeaderMapper;
import ru.codeninja.proxyapp.RequestParamParser;
import ru.codeninja.proxyapp.connection.AbstractUrlConnection;
import ru.codeninja.proxyapp.connection.HttpMethod;
import ru.codeninja.proxyapp.connection.UrlConnection;
import ru.codeninja.proxyapp.connection.UrlConnectionFactory;
import ru.codeninja.proxyapp.response.ResponseWriter;
import ru.codeninja.proxyapp.response.ResponseWriterFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

/**
 * Created: 22.01.15 20:47
 *
 * @author Vitaliy Mayorov
 */
public class ProxyServlet extends HttpServlet {
    final Logger l = Logger.getLogger(this.getClass().getName());

    UrlConnectionFactory urlConnectionFactory;
    RequestParamParser requestParamParser;
    HeaderMapper headerMapper;
    ResponseWriterFactory responseWriterFactory;

    @Override
    public void init() throws ServletException {
        super.init();

        urlConnectionFactory = new UrlConnectionFactory();
        requestParamParser = new RequestParamParser();
        headerMapper = new HeaderMapper();
        responseWriterFactory = new ResponseWriterFactory();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UrlConnection urlConnection = urlConnectionFactory.get(HttpMethod.POST);

        processRequest(urlConnection, req, resp);
    }

    private void processRequest(UrlConnection urlConnection, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = requestParamParser.getUrl(req);

        HttpURLConnection connection = urlConnection.connect(url, req);
        if (connection == null) {
            //todo implement an error page
        } else {
            headerMapper.setHeaders(resp, connection);

            ResponseWriter responseWriter = responseWriterFactory.get(connection);
            responseWriter.sendResponse(connection, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UrlConnection urlConnection = urlConnectionFactory.get(HttpMethod.GET);

        processRequest(urlConnection, req, resp);
    }
}
