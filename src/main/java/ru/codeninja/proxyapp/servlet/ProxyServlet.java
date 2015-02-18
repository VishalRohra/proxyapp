package ru.codeninja.proxyapp.servlet;

import ru.codeninja.proxyapp.connection.ProxyConnection;
import ru.codeninja.proxyapp.cookies.CookiesHandler;
import ru.codeninja.proxyapp.cookies.CookiesHandlerFactory;
import ru.codeninja.proxyapp.response.ResponseHeadersMapper;
import ru.codeninja.proxyapp.response.RequestParamParser;
import ru.codeninja.proxyapp.connection.HttpMethod;
import ru.codeninja.proxyapp.connection.UrlConnection;
import ru.codeninja.proxyapp.connection.UrlConnectionFactory;
import ru.codeninja.proxyapp.response.writer.ResponseWriter;
import ru.codeninja.proxyapp.response.ResponseWriterFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    ResponseHeadersMapper responseHeadersMapper;
    ResponseWriterFactory responseWriterFactory;
    CookiesHandlerFactory cookiesHandlerFactory;

    @Override
    public void init() throws ServletException {
        super.init();

        urlConnectionFactory = new UrlConnectionFactory();
        requestParamParser = new RequestParamParser();
        responseHeadersMapper = new ResponseHeadersMapper();
        responseWriterFactory = new ResponseWriterFactory();
        cookiesHandlerFactory = new CookiesHandlerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UrlConnection urlConnection = urlConnectionFactory.get(HttpMethod.POST);
        processRequest(urlConnection, req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UrlConnection urlConnection = urlConnectionFactory.get(HttpMethod.GET);
        processRequest(urlConnection, req, resp);
    }

    private void processRequest(UrlConnection urlConnection, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = requestParamParser.getUrl(req);

        ProxyConnection connection = urlConnection.connect(url, req);
        CookiesHandler cookiesHandler = cookiesHandlerFactory.getRule(req);
        cookiesHandler.sendCookies(req, connection);
        if (connection == null) {
            //todo implement an error page
        } else {
            responseHeadersMapper.setHeaders(resp, connection);
            cookiesHandler.receiveCookies(resp, connection);

            ResponseWriter responseWriter = responseWriterFactory.get(connection);
            responseWriter.sendResponse(connection, resp);
        }
    }

}
