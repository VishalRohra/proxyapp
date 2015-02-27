package ru.codeninja.proxyapp.request;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestedUrlTest {

    @Test
    public void empty() {
        HttpServletRequest request = mockRequest("/");

        RequestedUrl url = RequestedUrl.parse(request);
        assertNull(url);
    }

    @Test
    public void test() {

        assertEquals(RequestedUrl.parse(mockRequest("/google.com/test")).getUrl(), "http://google.com/test");
        {
            RequestedUrl url = RequestedUrl.parse(mockRequest("/s/google.com/test"));
            assertEquals(url.getUrl(), "https://google.com/test");
            assertTrue(url.isSslMode());
            assertFalse(url.isCookiesMode());
        }
        assertEquals(RequestedUrl.parse(mockRequest("/google.com/test", "q=abc123")).getUrl(), "http://google.com/test?q=abc123");
        assertEquals(RequestedUrl.parse(mockRequest("/s/google.com/test", "q=abc123")).getUrl(), "https://google.com/test?q=abc123");
        assertEquals(RequestedUrl.parse(mockRequest("/some_site.com/test/img/bg.png")).getUrl(), "http://some_site.com/test/img/bg.png");
        assertEquals(RequestedUrl.parse(mockRequest("/some_site.com/img/bg.png")).getUrl(), "http://some_site.com/img/bg.png");
        assertEquals(RequestedUrl.parse(mockRequest("/s/google.com:8080/test", "q=abc123&test1=ab123%32")).getUrl(), "https://google.com:8080/test?q=abc123&test1=ab123%32");
        assertNull(RequestedUrl.parse(mockRequest("/")));
        assertNull(RequestedUrl.parse(mockRequest("")));
        {
            RequestedUrl url = RequestedUrl.parse(mockRequest("/c/google.com/test"));
            assertFalse(url.isSslMode());
            assertTrue(url.isCookiesMode());
            assertEquals(url.getUrl(), "http://google.com/test");
        }
        {
            RequestedUrl url = RequestedUrl.parse(mockRequest("/cs/google.com/test"));
            assertTrue(url.isSslMode());
            assertTrue(url.isCookiesMode());
            assertEquals(url.getUrl(), "https://google.com/test");
        }
        assertEquals(RequestedUrl.parse(mockRequest("/sc/google.com/test")).getUrl(), "https://google.com/test");
    }

    private HttpServletRequest mockRequest(String path) {
        return mockRequest(path, null);
    }

    private HttpServletRequest mockRequest(String path, String query) {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo())
                .thenReturn(path);
        when(request.getQueryString())
                .thenReturn(query);

        return request;
    }
}