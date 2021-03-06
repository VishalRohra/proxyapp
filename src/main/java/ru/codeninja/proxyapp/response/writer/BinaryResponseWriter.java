package ru.codeninja.proxyapp.response.writer;

import ru.codeninja.proxyapp.connection.ProxyConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created: 23.01.15 11:49
 *
 * @author Vitaliy Mayorov
 */
public class BinaryResponseWriter implements ResponseWriter {
    private static final int READ_BUFFER_SIZE = 1024;

    @Override
    public void sendResponse(ProxyConnection connection, HttpServletResponse output) throws IOException {
        InputStream inputStream = connection.conn.getInputStream();

        byte[] buff = new byte[READ_BUFFER_SIZE];
        int count;
        ServletOutputStream outputStream = output.getOutputStream();
        while ((count = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, count);
        }

    }
}
