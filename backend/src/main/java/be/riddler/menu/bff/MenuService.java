package be.riddler.menu.bff;

import be.riddler.menu.external.MenuResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * MenuService
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MenuService {
    private final MenuResource menuResource;
    public @NonNull List<@NonNull Menu> menu() {
        return menuResource.menu();
    }
}
