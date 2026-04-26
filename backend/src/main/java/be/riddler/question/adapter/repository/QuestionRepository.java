package be.riddler.question.adapter.repository;

import be.riddler.question.adapter.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * QuestionRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
}
