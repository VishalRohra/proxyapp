package ru.codeninja.proxyapp.response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.codeninja.proxyapp.request.RequestParamParser;
import ru.codeninja.proxyapp.request.RequestedUrl;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created: 23.01.15 11:39
 *
 * @author Vitaliy Mayorov
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestParamParserTest {
    public static final String TEST_URL = "https://google.com/";
    @Mock
    HttpServletRequest request;

    RequestParamParser requestParamParser;

    @Before
    public void before() {
        requestParamParser = new RequestParamParser();
    }

    @Test
    public void empty() {
        when(request.getPathInfo())
                .thenReturn("/");
        RequestedUrl url = requestParamParser.getUrl(request);
        assertNull(url);
    }

    @Test
    public void param() {
        when(request.getPathInfo())
                .thenReturn("/test_site/test");
        when(request.getQueryString())
                .thenReturn("param1=1&param2=abc");

        RequestedUrl url = requestParamParser.getUrl(request);
        assertEquals("http://test_site/test?param1=1&param2=abc", url.getUrl());
        assertSame(request, url.getRequest());
        assertFalse(url.isCookiesOn());
    }

}
