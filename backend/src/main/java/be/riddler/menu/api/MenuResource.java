package be.riddler.menu.api;

import be.riddler.menu.bff.Menu;
import be.riddler.menu.port.MenuOutPort;
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
@RequestMapping(path = "/menu/v1")
class MenuResource implements MenuApi {
    private final MenuOutPort menuOutPort;

    @Override
    public List<Menu> menu() {
        return menuOutPort.findAll("")
                .stream()
                .map(menu -> new Menu(menu.getPath(), menu.getLabel(), menu.getIcon(), menu.getOrder()))
                .toList();
    }
}
