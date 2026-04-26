package be.riddler.domain.menu.adapter.repository;

import be.riddler.domain.menu.adapter.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * MenuJpaRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {
}
