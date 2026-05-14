package be.riddler.v1.settings.model;

/**
 * Bookmark
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
public record CreateBookmark(BookmarkType bookmarkType, String path, String label) {
}
