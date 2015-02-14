package ru.codeninja.proxyapp.response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.codeninja.proxyapp.response.RequestParamParser;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
        assertNull(requestParamParser.getUrl(request));
    }

    @Test
    public void param() {
        when(request.getParameter("url"))
                .thenReturn(TEST_URL);

        assertEquals(TEST_URL, requestParamParser.getUrl(request));
    }

}
