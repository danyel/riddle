package be.riddler.menu.port;

import be.riddler.menu.domain.Menu;

import java.util.List;

/**
 * MenuPort
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuRepository {
    List<Menu> findAll(String username);
}
