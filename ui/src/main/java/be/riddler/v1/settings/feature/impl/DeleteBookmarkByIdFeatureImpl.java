package be.riddler.v1.settings.feature.impl;

import be.riddler.v1.settings.feature.DeleteBookmarkByIdFeature;
import be.riddler.v1.settings.repository.BookmarkRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * DeleteBookmarkByIdFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DeleteBookmarkByIdFeatureImpl implements DeleteBookmarkByIdFeature {
    private final BookmarkRepository bookmarkRepository;

    @Override
    public void deleteBookmark(@NonNull UUID bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
