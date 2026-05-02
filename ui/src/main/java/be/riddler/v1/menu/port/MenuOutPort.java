package be.riddler.v1.menu.port;

import be.riddler.v1.menu.domain.Menu;

import java.util.List;

/**
 * MenuPort
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuOutPort {
    List<Menu> findAll(String username);
}
