package ru.codeninja.proxyapp.cookies;

import org.junit.Test;
import ru.codeninja.proxyapp.header.Cookies;
import ru.codeninja.proxyapp.url.CurrentUrl;

import static org.junit.Assert.assertEquals;

public class CookiesTest {

    public static final CurrentUrl PATH1 = new CurrentUrl("http://test.com");
    public static final CurrentUrl PATH2 = new CurrentUrl("http://test.com/page", true);

    @Test
    public void test() {
        //Set-Cookie: <name>=<value>[; <Max-Age>=<age>]
        //[; expires=<date>][; domain=<domain_name>]
        //[; path=<some_path>][; secure][; HttpOnly]

        assertEquals("", Cookies.neutralize(PATH1, ""));
        assertEquals("", Cookies.neutralize(PATH1, null));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "name=Bananas; expires=Sat, 02 May 2009 23:38:25 GMT"));
        assertEquals("name=\"Bananas%40\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "name=Bananas@; expires=Sat, 02 May 2009 23:38:25 GMT"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "name=Bananas; expires=Sat, 02 May 2009 23:38:25 GMT"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "name=Bananas; domain=some_site.com"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/blog\"", Cookies.neutralize(PATH1, "Set-Cookie: name=Bananas; path=/blog"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "name=Bananas;"));

        assertEquals("a=\"b\";$Path=\"/test.com/\"", Cookies.neutralize(PATH1, "a=b;Path=/;,c=d;Path=/;"));
        assertEquals("name=\"Bananas\";$Path=\"/test.com/\"", Cookies.neutralize(new CurrentUrl("http://test.com/test"), "name=Bananas; expires=Sat, 02 May 2009 23:38:25 GMT"));

        assertEquals("desu.session=\"29ec3f25108b2c2da02383b24151c304\";$Path=\"/c/test.com/\"", Cookies.neutralize(PATH2, "desu.session=29ec3f25108b2c2da02383b24151c304; path=/; expires=Fri, 26-Jun-2015 11:26:34 GMT"));
        assertEquals("desu.config.0=\"%257B%2522board.int.lastvisited%2522%253A%25221427541962%2522%252C%2522disclaimer_seen%2522%253A1%257D\";$Path=\"/c/test.com/\"", Cookies.neutralize(PATH2, "desu.config.0=%7B%22board.int.lastvisited%22%3A%221427541962%22%2C%22disclaimer_seen%22%3A1%7D; path=/; expires=Fri, 26-Jun-2015 11:26:34 GMT"));
        assertEquals("__uid=\"da002cc52e13bedf7bfee80ff59e9552c1427541609\";$Path=\"/c/test.com/\"", Cookies.neutralize(PATH2, "__uid=da002cc52e13bedf7bfee80ff59e9552c1427541609; path=/; expires=Fri, 26-Jun-2015 11:26:34 GMT"));

    }
}