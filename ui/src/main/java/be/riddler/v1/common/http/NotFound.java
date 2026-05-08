package be.riddler.v1.common.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * NotFound
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@RequiredArgsConstructor
@Getter
public class NotFound extends RuntimeException {
    private final String title;
    private final String description;
    private final Map<String, Object> properties;

}
