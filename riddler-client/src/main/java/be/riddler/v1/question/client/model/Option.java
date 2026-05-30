package be.riddler.v1.question.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Option
 *
 * @author dnoulet
 * @version 1.0.0 25/05/2026
 */
public record Option(@NonNull UUID id, @NonNull String value) {
}
