package be.riddler.v1.settings.model;

import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Activity
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record Settings(
        @NonNull String username,
        @NonNull List<@NonNull String> roles,
        @NonNull List<@NonNull Bookmark> bookmarks
) {
}
