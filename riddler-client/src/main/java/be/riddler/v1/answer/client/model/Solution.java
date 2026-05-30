package be.riddler.v1.answer.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Solution
 *
 * @author dnoulet
 * @version 1.0.0 25/05/2026
 */
public record Solution(@NonNull UUID id, @NonNull String value) {
}
