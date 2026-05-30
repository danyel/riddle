package be.riddler.v1.answer.client.model;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

/**
 * UpdateSolution
 *
 * @author dnoulet
 * @version 1.0.0 26/05/2026
 */
public record UpdateSolution(@Nullable UUID id, @NonNull String value) {
}
