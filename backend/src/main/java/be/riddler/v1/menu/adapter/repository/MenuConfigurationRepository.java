package be.riddler.v1.menu.adapter.repository;

import be.riddler.v1.menu.adapter.entity.MenuConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * MenuJpaRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuConfigurationRepository extends JpaRepository<MenuConfigurationEntity, UUID> {
    List<MenuConfigurationEntity> findAllByUsername(String username);
}
