package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.UrlEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by vital on 08.02.15.
 */
public class CssContentModifier implements ContentModifier {
    final Logger l = Logger.getLogger(this.getClass().getName());
    final Pattern[] patterns = {
            Pattern.compile("url[\\s]*\\(['\"]?([^'^\"^\\)]+)['\"]?\\)"), // css url(..)
            Pattern.compile("@import[\\s]*['\"]?([^'^\"^;]+)['\"]?"), // css @import
    };
    @Override
    public void modify(String currentUrl, BufferedReader inputReader, PrintWriter outputStream) {
        String line;

        try {
            UrlEncoder urlEncoder = new UrlEncoder(currentUrl);
            while ((line = inputReader.readLine()) != null) {
                StringBuffer lineBuff = PatternReplace.replace(patterns, line, urlEncoder);

                outputStream.println(lineBuff);
            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }

}
