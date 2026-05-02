package be.riddler.v1.ui.adapter.repository;

import be.riddler.v1.ui.adapter.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * TranslationRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface TranslationRepository extends JpaRepository<TranslationEntity, UUID> {
    List<TranslationEntity> findAllByLanguageAndKey_IdIn(String language, List<UUID> ids);
}
