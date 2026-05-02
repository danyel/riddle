package be.riddler.configuration.coding;

import feign.RequestTemplate;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static be.riddler.configuration.coding.HttpHeaderHelper.getHttpHeaders;

/// FeignOutputMessage
///
/// @author dnoulet
/// @version 1.0.0 04/04/2026
public class FeignOutputMessage implements HttpOutputMessage {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final HttpHeaders httpHeaders;

    public FeignOutputMessage(RequestTemplate request) {
        httpHeaders = getHttpHeaders(request.headers());
    }

    @Override
    @NonNull
    public OutputStream getBody() {
        return outputStream;
    }

    @Override
    @NonNull
    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    @NonNull
    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }
}
