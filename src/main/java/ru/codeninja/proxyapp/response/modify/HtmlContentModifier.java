package ru.codeninja.proxyapp.response.modify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vital on 08.02.15.
 */
public class HtmlContentModifier implements ContentModifier {
    final Logger l = Logger.getLogger(this.getClass().getName());

    @Override
    public void modify(BufferedReader inputReader, PrintWriter outputStream) {
        //todo modify
        String line;

        try {
            while ((line = inputReader.readLine()) != null) {

            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }
}
