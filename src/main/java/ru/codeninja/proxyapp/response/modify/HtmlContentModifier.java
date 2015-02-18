package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vital on 08.02.15.
 */
public class HtmlContentModifier extends AbstractRegExpContentModifier implements ContentModifier {

    static final String INJECTED_CODE = "<script src=\"/pr.js\"></script>";
    final static Pattern[] patterns = {
            Pattern.compile("href=['\"]?([^'^\"\\s]+)['\"]?"),
            Pattern.compile("src=['\"]?([^'^\"\\s]+)['\"]?"),
            Pattern.compile("action=['\"]?([^'^\"\\s]+)['\"]?"), // form
            Pattern.compile("url[\\s]*\\(['\"]?([^'^\"^\\)]+)['\"]?\\)"), // css url(..)
            Pattern.compile("@import[\\s]*['\"]?([^'^\"^;]+)['\"]?"), // css @import
    };

    final static Pattern HEAD = Pattern.compile("<head([\\s]*|[\\s]+[^>]+)>");

    @Override
    public void modify(CurrentUrl currentUrl, BufferedReader inputReader, PrintWriter outputStream) {
        String line;

        try {
            while ((line = inputReader.readLine()) != null) {
                StringBuffer lineBuff = replace(patterns, line, currentUrl);

                Matcher headMatcher = HEAD.matcher(lineBuff);
                while (headMatcher.find()) {
                    StringBuffer buf = new StringBuffer();
                    String headTag = headMatcher.group();
                    headMatcher.appendReplacement(buf, headTag);
                    buf.append("\n" + INJECTED_CODE);
                    headMatcher.appendTail(buf);
                    lineBuff = buf;
                }

                outputStream.println(lineBuff);
            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }
}
