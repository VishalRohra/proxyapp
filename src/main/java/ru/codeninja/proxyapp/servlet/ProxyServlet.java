package ru.codeninja.proxyapp.servlet;

import ru.codeninja.proxyapp.connection.*;
import ru.codeninja.proxyapp.header.Redirect;
import ru.codeninja.proxyapp.header.ResponseHeadersManager;
import ru.codeninja.proxyapp.request.RequestedUrl;
import ru.codeninja.proxyapp.response.ResponseWriterFactory;
import ru.codeninja.proxyapp.response.writer.ResponseWriter;
import ru.codeninja.proxyapp.url.CurrentUrl;

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
    ResponseHeadersManager responseHeadersManager;
    ResponseWriterFactory responseWriterFactory;

    @Override
    public void init() throws ServletException {
        super.init();

        urlConnectionFactory = new UrlConnectionFactory();
        responseHeadersManager = new ResponseHeadersManager();
        responseWriterFactory = new ResponseWriterFactory();
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
        RequestedUrl url = RequestedUrl.parse(req);

        if (url == null) {
            l.info("root page");
            if (req.getParameter("url") != null) {
                CurrentUrl currentUrl = new CurrentUrl("/", req.getParameter("cookiesMode") != null);
                Redirect.location(currentUrl, resp).to(req.getParameter("url"));
            } else {
                StaticContent.ROOT.doRequest(req, resp);
            }
        } else {
            if (SpyJsProtector.isSafe(url.getUrl())) {
                ProxyConnection connection = urlConnection.connect(url);
                if (connection == null) {
                    //todo implement an error page
                    l.warning("cannot connect an url");
                } else {
                    responseHeadersManager.sendHeaders(resp, connection);
                    //todo implement the header manager

                    ResponseWriter responseWriter = responseWriterFactory.get(connection);
                    responseWriter.sendResponse(connection, resp);
                }
            } else {
                // it's a spy url, let's do something
                resp.setStatus(200);
            }
        }
    }

}
