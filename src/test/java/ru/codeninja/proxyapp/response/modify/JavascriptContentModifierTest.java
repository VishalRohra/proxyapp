package ru.codeninja.proxyapp.response.modify;

import org.junit.Before;
import org.junit.Test;
import ru.codeninja.proxyapp.url.CurrentUrl;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class JavascriptContentModifierTest {

    public static final String SOURCE_FILE = "script.js";
    public static final String RESULT_FILE = "script_expect.js";
    JavascriptContentModifier modifier;

    @Before
    public void before() {
        modifier = new JavascriptContentModifier();
    }

    @Test
    public void test() throws Exception {
        BufferedReader bufferedReader = getBufferedReader(SOURCE_FILE);

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        modifier.modify(new CurrentUrl("http://some_page.com/test/", false), bufferedReader, printWriter);
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