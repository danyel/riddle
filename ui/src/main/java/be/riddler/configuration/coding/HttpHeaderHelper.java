package be.riddler.configuration.coding;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/// HttpHeaderHelper
///
/// @author dnoulet
/// @version 1.0.0 04/04/2026
@UtilityClass
public class HttpHeaderHelper {
    public static HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return httpHeaders;
    }
}
