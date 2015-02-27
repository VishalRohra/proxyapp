package ru.codeninja.proxyapp.connection;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpyJsProtectorTest {
    @Test
    public void test() {
        assertTrue(SpyJsProtector.isSafe("https://google.com/test"));
        assertFalse(SpyJsProtector.isSafe("https://www.google-analytics.com/ga.js"));
    }
}