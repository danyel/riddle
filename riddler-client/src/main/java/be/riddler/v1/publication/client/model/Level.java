package be.riddler.v1.publication.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * CreateLevel
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public record Level(@NonNull UUID id, @NonNull String level) {
}
