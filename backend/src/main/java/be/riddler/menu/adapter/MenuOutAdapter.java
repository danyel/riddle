package be.riddler.menu.adapter;

import be.riddler.menu.adapter.repository.MenuRepository;
import be.riddler.menu.domain.Menu;
import be.riddler.menu.port.MenuOutPort;
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

    @Override
    public List<Menu> findAll(String username) {
        return menuRepository.findAll()
                .stream()
                .map(menuEntity -> new Menu(menuEntity.getId(), menuEntity.getPath(), menuEntity.getLabel(), menuEntity.getIcon(), menuEntity.getOrder()))
                .sorted(Comparator.comparing(Menu::getOrder))
                .toList();
    }
}
