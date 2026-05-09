package be.riddler.v1.ui.bff;

import be.riddler.v1.ui.api.UiApi;
import be.riddler.v1.ui.domain.Translation;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

/**
 * QuestionTypeEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TranslateEndpoint {
    private final UiApi uiApi;

    public @NonNull Translation translate(String language, String key) {
        return uiApi.translate(language, key);
    }
}
