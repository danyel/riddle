package be.riddler.v1.bookmark.bff;

import be.riddler.v1.settings.client.SettingsClient;
import be.riddler.v1.settings.model.Bookmark;
import be.riddler.v1.settings.model.BookmarkType;
import be.riddler.v1.settings.model.CreateBookmark;
import be.riddler.v1.settings.model.Settings;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.stream.Streams;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * BookmarkEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BookmarkEndpoint {
    private final SettingsClient settingsClient;

    public @NonNull Settings bookmark(@NonNull CreateBookmark createBookmark) {
        return settingsClient.createBookmark(createBookmark);
    }

    public void deleteBookmark(@NonNull Bookmark bookmark) {
        settingsClient.deleteBookmark(bookmark.id());
    }

    public @NonNull List<@NonNull String> bookmarkTypes() {
        return Streams.of(BookmarkType.values())
                .map(Enum::name)
                .toList();
    }
}
