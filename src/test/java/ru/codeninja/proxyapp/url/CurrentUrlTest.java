package ru.codeninja.proxyapp.url;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created: 25.01.15 13:37
 *
 * @author Vitaliy Mayorov
 */
public class CurrentUrlTest {
    public static final String CURRENT_URL = "http://some_site.com/test/hello.php?test1=123&test2=abc";
    private CurrentUrl currentUrl;

    @Test
    public void test() {
        currentUrl = new CurrentUrl(CURRENT_URL, false);

        assertEquals("/google.com/test", currentUrl.encodeUrl("http:&#47;&#47;google.com&#47;test"));
        assertEquals("/s/google.com/test/", currentUrl.encodeUrl("https%3A%2F%2Fgoogle.com%2Ftest%2F"));
        assertEquals("/google.com/test", currentUrl.encodeUrl("http://google.com/test"));
        assertEquals("/s/google.com/test", currentUrl.encodeUrl("https://google.com/test"));
        assertEquals("/google.com/test", currentUrl.encodeUrl("//google.com/test"));
        assertEquals("/google.com/test?q=abc123", currentUrl.encodeUrl("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", currentUrl.encodeUrl("https://google.com/test?q=abc123"));
        assertEquals("/some_site.com/test/img/bg.png", currentUrl.encodeUrl("img/bg.png"));
        assertEquals("/some_site.com/test", currentUrl.encodeUrl("//some_site.com/test"));
        assertEquals("/some_site.com/test", currentUrl.encodeUrl("http://some_site.com/test"));
        assertEquals("/s/some_site.com/test", currentUrl.encodeUrl("https://some_site.com/test"));
        assertEquals("/some_site.com/img/bg.png", currentUrl.encodeUrl("/img/bg.png"));
        assertEquals("/s/google.com:8080/test?q=abc123&test1=123abc", currentUrl.encodeUrl("https://google.com:8080/test?q=abc123&test1=123abc"));
        assertEquals("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw==", currentUrl.encodeUrl("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw=="));
    }

    @Test
    public void cookies() {

        currentUrl = new CurrentUrl(CURRENT_URL, true);

        assertEquals("/google.com/test", currentUrl.encodeUrl("http:&#47;&#47;google.com&#47;test"));
        assertEquals("/s/google.com/test/", currentUrl.encodeUrl("https%3A%2F%2Fgoogle.com%2Ftest%2F"));
        assertEquals("/google.com/test", currentUrl.encodeUrl("http://google.com/test"));
        assertEquals("/s/google.com/test", currentUrl.encodeUrl("https://google.com/test"));
        assertEquals("/google.com/test", currentUrl.encodeUrl("//google.com/test"));
        assertEquals("/google.com/test?q=abc123", currentUrl.encodeUrl("http://google.com/test?q=abc123"));
        assertEquals("/s/google.com/test?q=abc123", currentUrl.encodeUrl("https://google.com/test?q=abc123"));
        assertEquals("/s/google.com:8080/test?q=abc123&test1=123abc", currentUrl.encodeUrl("https://google.com:8080/test?q=abc123&test1=123abc"));
        assertEquals("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw==", currentUrl.encodeUrl("data:image/gif;base64,R0lGODlhDgAOAJEAAPDw8IyMjP///wAAACH5BAEAAAIALAAAAAAOAA4AQAIUVI55pu0Pozyg2jqz3u9evjGRUAAAOw=="));

        assertEquals("/some_site.com/test/img/bg.png?__cookies", currentUrl.encodeUrl("img/bg.png"));
        assertEquals("/some_site.com/img/bg.png?__cookies", currentUrl.encodeUrl("/img/bg.png"));
        assertEquals("/some_site.com/test?__cookies", currentUrl.encodeUrl("http://some_site.com/test"));
        assertEquals("/s/some_site.com/test?__cookies", currentUrl.encodeUrl("https://some_site.com/test"));
        assertEquals("/some_site.com/?__cookies", currentUrl.encodeUrl("/"));
    }
}
