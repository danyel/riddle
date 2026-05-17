package be.riddler.v1.participant.repository;

import be.riddler.v1.participant.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * CategoryRepository
 *
 * @author dnoulet
 * @version 1.0.0 16/05/2026
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByNameIgnoreCase(String categoryName);
}
