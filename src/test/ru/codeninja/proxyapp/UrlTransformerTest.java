package ru.codeninja.proxyapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Copyright 2009-2012. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 25.01.15 13:37
 *
 * @author Vitaliy Mayorov
 */
public class UrlTransformerTest {
    public static final String CURRENT_URL = "http://some_site.com/test/hello.php?test1=123&test2=abc";
    private UrlTransformer urlTransformer;

    @Before
    public void before() {
        urlTransformer = new UrlTransformer(CURRENT_URL);
    }

    @Test
    public void test() {
        assertEquals("/google.com/test", urlTransformer.encode("http://google.com/test"));
        assertEquals("/s/google.com/test", urlTransformer.encode("https://google.com/test"));
        assertEquals("/google.com/test?q=abc123", urlTransformer.encode("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", urlTransformer.encode("https://google.com/test?q=abc123"));
        assertEquals("/some_site.com/test/img/bg.png", urlTransformer.encode("img/bg.png"));
        assertEquals("/some_site.com/img/bg.png", urlTransformer.encode("/img/bg.png"));
    }
}
