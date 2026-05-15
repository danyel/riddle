package be.riddler.configuration.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * JwtAuthenticationFilter
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@Component
class CorrelationIdFilter extends OncePerRequestFilter {
    public static final String MDC_CORRELATION_ID = "correlationId";
    public static final String X_CORRELATION_ID = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        var correlationId = UUID.randomUUID().toString();
        var correlationIdHeaderValue = request.getHeader(X_CORRELATION_ID);

        if (correlationIdHeaderValue != null) {
            correlationId = correlationIdHeaderValue;
        }

        MDC.put(MDC_CORRELATION_ID, correlationId);
        filterChain.doFilter(request, response);
        response.addHeader(X_CORRELATION_ID, correlationId);
        MDC.remove(MDC_CORRELATION_ID);
    }
}
