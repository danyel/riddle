package be.riddler.question.port.out;

import be.riddler.question.domain.Question;

import java.util.List;

/**
 * QuestionRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public interface QuestionRepository {
    List<Question> getQuestions();
}
