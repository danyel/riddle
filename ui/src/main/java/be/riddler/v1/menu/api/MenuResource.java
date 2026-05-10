package be.riddler.v1.menu.api;

import be.riddler.v1.menu.domain.Menu;
import be.riddler.v1.menu.feature.RetrieveMenuForUsernameFeature;
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
class MenuResource implements MenuClient {
    private final RetrieveMenuForUsernameFeature retrieveMenuForUsernameFeature;

    @Override
    public List<Menu> menu(String username) {
        return retrieveMenuForUsernameFeature.retrieveMenu(username);
    }
}
