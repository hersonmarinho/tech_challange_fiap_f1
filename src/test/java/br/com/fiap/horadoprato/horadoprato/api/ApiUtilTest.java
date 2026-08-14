package br.com.fiap.horadoprato.horadoprato.api;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiUtilTest {

    @Test
    void deveSetarExemploNoResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        ApiUtil.setExampleResponse(webRequest, "application/json", "{\"ok\":true}");

        assertEquals("UTF-8", response.getCharacterEncoding());
        assertEquals("application/json;charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("{\"ok\":true}", new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }

    @Test
    void deveLancarRuntimeExceptionQuandoWriterFalhar() throws IOException {
        NativeWebRequest request = mock(NativeWebRequest.class);
        jakarta.servlet.http.HttpServletResponse response = mock(jakarta.servlet.http.HttpServletResponse.class);

        when(request.getNativeResponse(jakarta.servlet.http.HttpServletResponse.class)).thenReturn(response);
        when(response.getWriter()).thenThrow(new IOException("falha"));

        assertThrows(RuntimeException.class,
                () -> ApiUtil.setExampleResponse(request, "application/json", "{\"ok\":true}"));
    }
}
