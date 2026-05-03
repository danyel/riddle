package be.riddler.v1.ui.bff;

import be.riddler.v1.ui.api.UiApi;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * QuestionTypeEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class QuestionTypeEndpoint {
    private final UiApi uiApi;

    public @NonNull List<@NonNull String> questionTypes() {
        return uiApi.questionTypes();
    }
}
