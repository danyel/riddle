package be.riddler.v1.settings.model;

import java.util.UUID;

/**
 * Bookmark
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public record Bookmark(UUID id, BookmarkType bookmarkType, String path, String label) {
}
