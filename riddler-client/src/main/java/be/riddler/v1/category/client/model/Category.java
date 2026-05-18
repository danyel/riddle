package be.riddler.v1.category.client.model;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Category
 *
 * @author dnoulet
 * @version 1.0.0 17/05/2026
 */
public record Category(@NonNull UUID id, @NonNull String name, @NonNull List<@NonNull Keyword> keywords) {
}
