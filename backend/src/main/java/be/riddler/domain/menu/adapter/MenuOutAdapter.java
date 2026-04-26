package be.riddler.domain.menu.adapter;

import be.riddler.domain.menu.adapter.entity.MenuConfigurationEntity;
import be.riddler.domain.menu.adapter.repository.MenuConfigurationRepository;
import be.riddler.domain.menu.adapter.repository.MenuRepository;
import be.riddler.domain.menu.domain.Menu;
import be.riddler.domain.menu.port.MenuOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * MenuLocalRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MenuOutAdapter implements MenuOutPort {
    private final MenuRepository menuRepository;
    private final MenuConfigurationRepository menuConfigurationRepository;

    @Override
    public List<Menu> findAll(String username) {
        var menuConfigurations = menuConfigurationRepository.findAllByUsername(username);
        return menuRepository.findAllById(menuConfigurations.stream()
                        .map(MenuConfigurationEntity::getMenuId)
                        .toList())
                .stream()
                .map(menuEntity -> new Menu(menuEntity.getId(), menuEntity.getPath(), menuEntity.getLabel(), menuEntity.getIcon(), menuEntity.getOrder()))
                .sorted(Comparator.comparing(Menu::getOrder))
                .toList();
    }
}
