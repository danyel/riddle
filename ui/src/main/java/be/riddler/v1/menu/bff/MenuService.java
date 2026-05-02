package be.riddler.v1.menu.bff;

import be.riddler.v1.menu.api.Menu;
import be.riddler.v1.menu.api.MenuApi;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
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
@PermitAll
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MenuService {
    private final MenuApi menuApi;

    public @NonNull List<@NonNull Menu> menu() {
        return menuApi.menu("dnoulet");
    }
}
