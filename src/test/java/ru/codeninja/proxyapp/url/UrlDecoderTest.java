package ru.codeninja.proxyapp.url;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created: 25.01.15 14:42
 *
 * @author Vitaliy Mayorov
 */
public class UrlDecoderTest {
    private UrlDecoder urlDecoder;

    @Before
    public void before() {
        urlDecoder = new UrlDecoder();
    }

    @Test
    public void test() {
        assertEquals(urlDecoder.decode("/google.com/test"), "http://google.com/test");
        assertEquals(urlDecoder.decode("/s/google.com/test"), "https://google.com/test");
        assertEquals(urlDecoder.decode("/google.com/test?q=abc123"), "http://google.com/test?q=abc123");
        assertEquals(urlDecoder.decode("/s/google.com/test?q=abc123"), "https://google.com/test?q=abc123");
        assertEquals(urlDecoder.decode("/some_site.com/test/img/bg.png"), "http://some_site.com/test/img/bg.png");
        assertEquals(urlDecoder.decode("/some_site.com/img/bg.png"), "http://some_site.com/img/bg.png");
        assertEquals(urlDecoder.decode("/s/google.com:8080/test?q=abc123&test1=ab123%32"), "https://google.com:8080/test?q=abc123&test1=ab123%32");
        assertEquals(urlDecoder.decode("/"), "/");
        assertEquals(urlDecoder.decode(""), "");
        assertEquals(urlDecoder.decode("/google.com/test?__cookies"), "http://google.com/test");
        assertEquals(urlDecoder.decode("/s/google.com/test?__cookies"), "https://google.com/test");
        assertEquals(urlDecoder.decode("/google.com/?__cookies"), "http://google.com/");
        assertEquals(urlDecoder.decode("/google.com?__cookies"), "http://google.com");
        assertEquals(urlDecoder.decode("/c/google.com/test"), "http://google.com/test");
        assertEquals(urlDecoder.decode("/cs/google.com/test"), "https://google.com/test");
        assertEquals(urlDecoder.decode("/sc/google.com/test"), "https://google.com/test");
    }
}
