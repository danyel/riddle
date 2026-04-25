package be.riddler.answer.port;

import be.riddler.answer.domain.Answer;

import java.util.List;
import java.util.UUID;

/**
 * AnswerRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public interface AnswerRepository {
    List<Answer> findByQuestion(UUID questionId);
}
