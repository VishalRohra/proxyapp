package ru.codeninja.proxyapp.response.modify;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by vital on 08.02.15.
 */
public interface ContentModifier {
    public void modify(String currentUrl, BufferedReader inputReader, PrintWriter outputStream);
}
