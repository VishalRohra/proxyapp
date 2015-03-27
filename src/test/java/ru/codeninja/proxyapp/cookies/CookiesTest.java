package ru.codeninja.proxyapp.cookies;

import org.junit.Test;
import ru.codeninja.proxyapp.header.Cookies;
import ru.codeninja.proxyapp.url.CurrentUrl;

import static org.junit.Assert.assertEquals;

public class CookiesTest {

    public static final CurrentUrl PATH = new CurrentUrl("http://test.com");

    @Test
    public void test() {
        //Set-Cookie: <name>=<value>[; <Max-Age>=<age>]
        //[; expires=<date>][; domain=<domain_name>]
        //[; path=<some_path>][; secure][; HttpOnly]

        assertEquals("", Cookies.neutralize(PATH, ""));
        assertEquals("", Cookies.neutralize(PATH, null));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH, "name=Bananas; expires=Sat, 02 May 2009 23:38:25 GMT"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH, "name=Bananas; domain=some_site.com"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/blog\"", Cookies.neutralize(PATH, "Set-Cookie: name=Bananas; path=/blog"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH, "name=Bananas;"));

        assertEquals("a=\"b\";$Path=\"/test.com/\"", Cookies.neutralize(PATH, "a=b;Path=/;,c=d;Path=/;"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(new CurrentUrl("http://test.com/test"), "name=Bananas; expires=Sat, 02 May 2009 23:38:25 GMT"));
    }
}