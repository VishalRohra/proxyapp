package ru.codeninja.proxyapp.url;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created: 25.01.15 13:37
 *
 * @author Vitaliy Mayorov
 */
public class UrlTransformerTest {
    public static final String CURRENT_URL = "http://some_site.com/test/hello.php?test1=123&test2=abc";
    private UrlEncoder urlEncoder;

    @Before
    public void before() {
        urlEncoder = new UrlEncoder(CURRENT_URL);
    }

    @Test
    public void test() {
        assertEquals("/google.com/test", urlEncoder.encode("http://google.com/test"));
        assertEquals("/s/google.com/test", urlEncoder.encode("https://google.com/test"));
        assertEquals("/google.com/test?q=abc123", urlEncoder.encode("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", urlEncoder.encode("https://google.com/test?q=abc123"));
        assertEquals("/some_site.com/test/img/bg.png", urlEncoder.encode("img/bg.png"));
        assertEquals("/some_site.com/img/bg.png", urlEncoder.encode("/img/bg.png"));
    }
}
