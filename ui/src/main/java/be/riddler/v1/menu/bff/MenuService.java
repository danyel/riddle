package be.riddler.v1.menu.bff;

import be.riddler.v1.menu.api.MenuClient;
import be.riddler.v1.menu.domain.Menu;
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
    private final MenuClient menuClient;

    public @NonNull List<@NonNull Menu> menu() {
        return menuClient.menu("dnoulet");
    }
}
