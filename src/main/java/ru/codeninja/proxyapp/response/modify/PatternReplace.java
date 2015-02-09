package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.UrlEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vital on 09.02.15.
 */
public class PatternReplace {
    public static StringBuffer replace(Pattern[] patterns, String line, UrlEncoder urlEncoder) {
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
        return lineBuff;
    }
}
