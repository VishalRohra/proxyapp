package ru.codeninja.proxyapp.response;

import org.junit.Test;
import ru.codeninja.proxyapp.connection.ProxyConnection;
import ru.codeninja.proxyapp.response.writer.HtmlResponseWriter;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ResponseWriterFactoryTest {
    @Test
    public void get() {
        ResponseWriterFactory factory = new ResponseWriterFactory();

        HttpURLConnection connection = mock(HttpURLConnection.class);
        ProxyConnection proxyConnection = new ProxyConnection(connection, false);

        doReturn("text/html;")
                .when(connection)
                .getContentType();

        assertTrue(factory.get(proxyConnection) instanceof HtmlResponseWriter);
    }

}