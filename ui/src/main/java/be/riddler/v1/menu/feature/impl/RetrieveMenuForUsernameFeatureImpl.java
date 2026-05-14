package be.riddler.v1.menu.feature.impl;

import be.riddler.v1.menu.client.model.Menu;
import be.riddler.v1.menu.entity.MenuConfigurationEntity;
import be.riddler.v1.menu.feature.RetrieveMenuForUsernameFeature;
import be.riddler.v1.menu.repository.MenuConfigurationRepository;
import be.riddler.v1.menu.repository.MenuRepository;
import be.riddler.v1.settings.constants.UserContext;
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
class RetrieveMenuForUsernameFeatureImpl implements RetrieveMenuForUsernameFeature {
    private final MenuRepository menuRepository;
    private final MenuConfigurationRepository menuConfigurationRepository;

    @Override
    public @NonNull List<@NonNull Menu> retrieveMenu() {
        var menuConfigurations = menuConfigurationRepository.findAllByRoleIn(UserContext.roles().stream().map(d -> d.replace("ROLE_", "")).toList());
        return menuRepository.findAllById(menuConfigurations.stream()
                        .map(MenuConfigurationEntity::getMenuId)
                        .toList())
                .stream()
                .map(menuEntity -> new Menu(menuEntity.getPath(), menuEntity.getLabel(), menuEntity.getIcon(), menuEntity.getBookmarkType(), menuEntity.getOrder()))
                .sorted(Comparator.comparing(Menu::order))
                .toList();
    }
}
