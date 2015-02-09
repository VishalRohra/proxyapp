package ru.codeninja.proxyapp.response.modify;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class HtmlContentModifierTest {
    public static final String SOURCE_FILE = "ru/codeninja/proxyapp/response/modify/page.html";
    public static final String RESULT_FILE = "ru/codeninja/proxyapp/response/modify/page_expect.html";
    HtmlContentModifier modifier;

    @Before
    public void before() {
        modifier = new HtmlContentModifier();
    }

    @Test
    public void test() throws Exception {
        BufferedReader bufferedReader = getBufferedReader(SOURCE_FILE);

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        modifier.modify("http://some_page.com/test/", bufferedReader, printWriter);
        printWriter.flush();

        BufferedReader expectBufferReader = getBufferedReader(RESULT_FILE);
        StringWriter expect = new StringWriter();
        PrintWriter expectPrint = new PrintWriter(expect);

        String line;
        while ((line = expectBufferReader.readLine()) != null) {
            expectPrint.println(line);
        }

        expectBufferReader.close();
        bufferedReader.close();

        assertEquals(expect.getBuffer().toString(), writer.getBuffer().toString());
    }

    private BufferedReader getBufferedReader(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(filePath);
        return new BufferedReader(new InputStreamReader(in));
    }

}