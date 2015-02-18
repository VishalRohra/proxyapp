package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 13.02.15.
 */
public class JavascriptContentModifier implements ContentModifier {
    protected final Logger l = Logger.getLogger(this.getClass().getName());

    @Override
    public void modify(CurrentUrl currentUrl, BufferedReader inputReader, PrintWriter outputStream) {
        String line;

        try {
            while ((line = inputReader.readLine()) != null) {
                outputStream.println(line.replaceAll("window.location", "window._location")); //pretty straightforward
            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }
}
