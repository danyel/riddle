package be.riddler.v1.menu.api;

import be.riddler.api.v1.menu.Menu;
import be.riddler.api.v1.menu.MenuApi;
import be.riddler.v1.menu.port.MenuOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MenuResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping(path = "/v1/menus")
class MenuResource implements MenuApi {
    private final MenuOutPort menuOutPort;

    @Override
    public List<Menu> menu(String username) {
        return menuOutPort.findAll(username)
                .stream()
                .map(menu -> new Menu("/secured%s".formatted(menu.getPath()), menu.getLabel(), menu.getIcon(), menu.getOrder()))
                .toList();
    }
}
