package be.riddler.configuration.coding;

import feign.Response;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;

import static be.riddler.configuration.coding.HttpHeaderHelper.getHttpHeaders;

/// FeignResponseAdapter
///
/// @author dnoulet
/// @version 1.0.0 04/04/2026
public record FeignResponseAdapter(Response response) implements ClientHttpResponse {
    @Override
    @NonNull
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.valueOf(response.status());
    }

    @Override
    @NonNull
    public String getStatusText() {
        return response.reason();
    }

    @Override
    public void close() {
        try {
            response.body().close();
        } catch (IOException ex) {
            // Ignore exception on close...
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public InputStream getBody() throws IOException {
        return response.body() != null ? response.body().asInputStream() : null;
    }

    @Override
    @NonNull
    public HttpHeaders getHeaders() {
        return getHttpHeaders(response.headers());
    }
}
