package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vital on 09.02.15.
 */
public class AbstractRegExpContentModifier {

    protected final Logger l = Logger.getLogger(this.getClass().getName());

    protected StringBuffer replace(Pattern[] patterns, String line, CurrentUrl currentUrl) {
        StringBuffer lineBuff = new StringBuffer(line);
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(lineBuff);

            int prevPos = 0;
            StringBuffer str = new StringBuffer();
            while (matcher.find()) {
                str.append(lineBuff.substring(prevPos, matcher.start(1)));
                str.append(currentUrl.encodeUrl(matcher.group(1)));
                prevPos = matcher.end(1);
            }

            if (prevPos > 0) {
                // append tail
                str.append(lineBuff.substring(prevPos, lineBuff.length()));
                lineBuff = str;
            }
        }

        return lineBuff;
    }
}
