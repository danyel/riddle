package be.riddler.question.port;

import be.riddler.question.domain.Question;

import java.util.List;
import java.util.UUID;

/**
 * QuestionRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public interface QuestionOutPort {
    List<Question> getQuestions();

    Question findById(UUID uuid);
}
