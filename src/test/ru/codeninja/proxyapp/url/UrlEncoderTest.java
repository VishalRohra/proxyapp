package ru.codeninja.proxyapp.url;

import org.junit.Before;
import org.junit.Test;
import ru.codeninja.proxyapp.cookies.CookiesHandler;

import static org.junit.Assert.assertEquals;
/**
 * Created: 25.01.15 13:37
 *
 * @author Vitaliy Mayorov
 */
public class UrlEncoderTest {
    public static final String CURRENT_URL = "http://some_site.com/test/hello.php?test1=123&test2=abc";
    private UrlEncoder urlEncoder;

    @Before
    public void before() {
        urlEncoder = new UrlEncoder(CURRENT_URL);
    }

    @Test
    public void test() {
        urlEncoder = new UrlEncoder(CURRENT_URL + "&" + CookiesHandler.COOKIES_ON_PARAM);

        assertEquals("/google.com/test", urlEncoder.encode("http:&#47;&#47;google.com&#47;test"));
        assertEquals("/s/google.com/test/", urlEncoder.encode("https%3A%2F%2Fgoogle.com%2Ftest%2F"));
        assertEquals("/google.com/test", urlEncoder.encode("http://google.com/test"));
        assertEquals("/s/google.com/test", urlEncoder.encode("https://google.com/test"));
        assertEquals("/google.com/test", urlEncoder.encode("//google.com/test"));
        assertEquals("/google.com/test?q=abc123", urlEncoder.encode("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", urlEncoder.encode("https://google.com/test?q=abc123"));
        assertEquals("/some_site.com/test/img/bg.png?__cookies", urlEncoder.encode("img/bg.png"));
        assertEquals("/some_site.com/test?__cookies", urlEncoder.encode("//some_site.com/test"));
        assertEquals("/some_site.com/test?__cookies", urlEncoder.encode("http://some_site.com/test"));
        assertEquals("/s/some_site.com/test?__cookies", urlEncoder.encode("https://some_site.com/test"));
        assertEquals("/some_site.com/img/bg.png?__cookies", urlEncoder.encode("/img/bg.png"));
        assertEquals("/s/google.com:8080/test?q=abc123&test1=123abc", urlEncoder.encode("https://google.com:8080/test?q=abc123&test1=123abc"));
        assertEquals("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw==", urlEncoder.encode("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw=="));
    }

    @Test
    public void cookies() {

        urlEncoder = new UrlEncoder(CURRENT_URL + "?" + CookiesHandler.COOKIES_ON_PARAM);

        assertEquals("/google.com/test", urlEncoder.encode("http:&#47;&#47;google.com&#47;test"));
        assertEquals("/s/google.com/test/", urlEncoder.encode("https%3A%2F%2Fgoogle.com%2Ftest%2F"));
        assertEquals("/google.com/test", urlEncoder.encode("http://google.com/test"));
        assertEquals("/s/google.com/test", urlEncoder.encode("https://google.com/test"));
        assertEquals("/google.com/test", urlEncoder.encode("//google.com/test"));
        assertEquals("/google.com/test?q=abc123", urlEncoder.encode("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", urlEncoder.encode("https://google.com/test?q=abc123"));
        assertEquals("/s/google.com:8080/test?q=abc123&test1=123abc", urlEncoder.encode("https://google.com:8080/test?q=abc123&test1=123abc"));
        assertEquals("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw==", urlEncoder.encode("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw=="));

        assertEquals("/some_site.com/test/img/bg.png?__cookies", urlEncoder.encode("img/bg.png"));
        assertEquals("/some_site.com/img/bg.png?__cookies", urlEncoder.encode("/img/bg.png"));
        assertEquals("/some_site.com/test?__cookies", urlEncoder.encode("http://some_site.com/test"));
        assertEquals("/s/some_site.com/test?__cookies", urlEncoder.encode("https://some_site.com/test"));
        assertEquals("/some_site.com/?__cookies", urlEncoder.encode("/"));
    }
}
