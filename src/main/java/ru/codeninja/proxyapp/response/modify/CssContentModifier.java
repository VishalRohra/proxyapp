package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Created by vital on 08.02.15.
 */
public class CssContentModifier extends AbstractRegExpContentModifier implements ContentModifier {
    final static Pattern[] patterns = {
            Pattern.compile("url[\\s]*\\(['\"]?([^'^\"^\\)]+)['\"]?\\)"), // css url(..)
            Pattern.compile("@import[\\s]*['\"]?([^'^\"^;]+)['\"]?"), // css @import
    };
    @Override
    public void modify(CurrentUrl currentUrl, BufferedReader inputReader, PrintWriter outputStream) {
        String line;

        try {
            while ((line = inputReader.readLine()) != null) {
                StringBuffer lineBuff = replace(patterns, line, currentUrl);

                outputStream.println(lineBuff);
            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }

}
