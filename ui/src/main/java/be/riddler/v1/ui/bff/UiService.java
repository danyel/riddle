package be.riddler.v1.ui.bff;

import be.riddler.v1.ui.api.UiApi;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * ComponentService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UiService {
    private final UiApi uiApi;

    public @NonNull List<@NonNull String> icons() {
        return uiApi.icons();
    }
}
