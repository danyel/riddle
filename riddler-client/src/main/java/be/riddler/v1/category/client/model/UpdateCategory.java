package be.riddler.v1.category.client.model;

import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * CreateCategory
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
public record UpdateCategory(@NonNull String name, @NonNull List<@NonNull UpdateKeyword> keywords) {
}
