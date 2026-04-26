package be.riddler.domain.answer.adapter.repository;

import be.riddler.domain.answer.adapter.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * QuestionRepository
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
    List<AnswerEntity> findAllByQuestionId(UUID questionId);
}
