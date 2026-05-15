package be.riddler.v1.settings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Bookmark
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public record Bookmark(UUID id, @NonNull @JsonProperty("bookmark_type") BookmarkType bookmarkType, @NonNull String path,
                       @NonNull String label) {
}
