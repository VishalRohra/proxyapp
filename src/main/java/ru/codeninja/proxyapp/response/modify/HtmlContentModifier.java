package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.UrlEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vital on 08.02.15.
 */
public class HtmlContentModifier implements ContentModifier {
    final Logger l = Logger.getLogger(this.getClass().getName());
    final Pattern[] patterns = {
            Pattern.compile("href=['\"]?([^'^\"\\s]+)['\"]?"),
            Pattern.compile("src=['\"]?([^'^\"\\s]+)['\"]?")
    };

    @Override
    public void modify(String currentUrl, BufferedReader inputReader, PrintWriter outputStream) {
        //todo modify
        String line;

        try {
            UrlEncoder urlEncoder = new UrlEncoder(currentUrl);
            while ((line = inputReader.readLine()) != null) {
                StringBuffer lineBuff = new StringBuffer(line);
                for (Pattern pattern : patterns) {
                    Matcher matcher = pattern.matcher(lineBuff);
                    while (matcher.find()) {
                        StringBuffer str = new StringBuffer();
                        str.append(lineBuff.substring(0, matcher.start(1)));
                        str.append(urlEncoder.encode(matcher.group(1)));
                        str.append(lineBuff.substring(matcher.end(1), lineBuff.length()));

                        lineBuff = str;
                    }
                }

                outputStream.println(lineBuff);
            }
        } catch (IOException e) {
            l.log(Level.WARNING, "cannot read input stream", e);
        }
    }
}
