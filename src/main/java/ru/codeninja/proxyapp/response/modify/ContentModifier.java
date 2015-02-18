package ru.codeninja.proxyapp.response.modify;

import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by vital on 08.02.15.
 */
public interface ContentModifier {
    public void modify(CurrentUrl currentUrl, BufferedReader inputReader, PrintWriter outputStream);
}
