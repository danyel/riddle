package be.riddler.v1.menu.client.model;

import be.riddler.v1.settings.model.BookmarkType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

/**
 * Menu
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public record Menu(@NonNull String path, @NonNull String label, @NonNull String icon,
                   @NonNull @JsonProperty(value = "bookmark_type") BookmarkType bookmarkType, int order) {
}
