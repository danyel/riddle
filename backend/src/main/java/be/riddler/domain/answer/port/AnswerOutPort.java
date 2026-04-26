package be.riddler.domain.answer.port;

import be.riddler.domain.answer.domain.Answer;

import java.util.List;
import java.util.UUID;

/**
 * AnswerRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public interface AnswerOutPort {
    List<Answer> findByQuestion(UUID questionId);
}
