package be.riddler.menu.adapter;

import be.riddler.menu.domain.Menu;
import be.riddler.menu.port.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * MenuLocalRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Component
class MenuLocalRepository implements MenuRepository {
    @Override
    public List<Menu> findAll(String username) {
        return Stream.of(
                new Menu(UUID.randomUUID(), "/question", "Question", "vaadin:envelope", 1),
                new Menu(UUID.randomUUID(), "/questions", "Questions", "vaadin:dashboard", 0),
                new Menu(UUID.randomUUID(), "/icons", "icons", "vaadin:dashboard", 2)
        ).sorted(Comparator.comparing(Menu::getOrder)).toList();
    }
}
