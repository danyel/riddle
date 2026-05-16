package be.riddler.v1.publication.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Publication
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public record Publication(
        @NonNull UUID id,
        @NonNull String title,
        @NonNull String description,
        @NonNull String proposal,
        @NonNull Position position,
        @NonNull Level level
) {
}
