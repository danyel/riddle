package be.riddler.domain.ui.bff;

import be.riddler.domain.ui.api.UiApi;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
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
@PermitAll
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ComponentService {
    private final UiApi uiApi;

    public @NonNull List<@NonNull String> icons() {
        return uiApi.icons();
    }
}
