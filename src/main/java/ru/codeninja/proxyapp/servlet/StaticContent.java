package ru.codeninja.proxyapp.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 06.03.15.
 */
public class StaticContent {
    public final static StaticContent ROOT = new StaticContent("index.html");
    final Logger l = Logger.getLogger(this.getClass().getName());

    private String filePath;

    private StaticContent(String filePath) {
        this.filePath = filePath;
    }

    public void doRequest(HttpServletRequest req, HttpServletResponse resp) {
        File file = new File(filePath);


        try {
            FileInputStream in = new FileInputStream(new File(filePath));
            FileChannel inChannel = in.getChannel();
            WritableByteChannel outChannel = Channels.newChannel(resp.getOutputStream());

            fastChannelCopy(inChannel, outChannel);
            inChannel.close();
            outChannel.close();

            resp.setContentType("text/html");
            resp.setStatus(200);
            resp.setCharacterEncoding("utf-8");

        } catch (FileNotFoundException e) {
            l.log(Level.SEVERE, "file not found", e);
        } catch (IOException e) {
            l.log(Level.SEVERE, "cannot read file", e);
        }
    }

    private void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining())dest.write(buffer);
    }
}
