package ru.codeninja.proxyapp.response;

import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseWriterFactoryTest {
    @Test
    public void get() {
        ResponseWriterFactory factory = new ResponseWriterFactory();

        HttpURLConnection connection = mock(HttpURLConnection.class);
        doReturn("text/html;")
                .when(connection)
                .getContentType();

        assertTrue(factory.get(connection) instanceof HtmlResponseWriter);
    }

}