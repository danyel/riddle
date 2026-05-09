package be.riddler.v1.menu.domain.feature;

import be.riddler.v1.menu.domain.Menu;
import be.riddler.v1.menu.entity.MenuConfigurationEntity;
import be.riddler.v1.menu.repository.MenuConfigurationRepository;
import be.riddler.v1.menu.repository.MenuRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * FindAllByUsernameFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RetrieveMenuForUsernameFeature {
    private final MenuRepository menuRepository;
    private final MenuConfigurationRepository menuConfigurationRepository;

    public @NonNull List<@NonNull Menu> retrieveMenu(@NonNull String username) {
        var menuConfigurations = menuConfigurationRepository.findAllByUsername(username);
        return menuRepository.findAllById(menuConfigurations.stream()
                        .map(MenuConfigurationEntity::getMenuId)
                        .toList())
                .stream()
                .map(menuEntity -> new Menu(menuEntity.getPath(), menuEntity.getLabel(), menuEntity.getIcon(), menuEntity.getOrder()))
                .sorted(Comparator.comparing(Menu::order))
                .toList();
    }
}
