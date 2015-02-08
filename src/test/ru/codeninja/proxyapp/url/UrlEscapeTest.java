package ru.codeninja.proxyapp.url;

import org.junit.Test;

import static org.junit.Assert.*;

public class UrlEscapeTest {

    @Test
    public void testEncode() {
        assertEquals("/test.com%3A8080/aba?test=123&", UrlEscape.encode("/test.com:8080/aba?test=123&"));
    }

    @Test
    public void testDecode() {
        assertEquals("/test.com:8080/aba?test=123&", UrlEscape.decode("/test.com%3A8080/aba?test=123&"));
    }
}