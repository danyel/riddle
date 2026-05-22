package be.riddler.v1.question.adapter.repository;

import be.riddler.common.repository.AbstractRepositoryTest;
import be.riddler.v1.question.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static be.riddler.v1.question.client.model.QuestionType.OPEN;
import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * QuestionRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
class QuestionRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findAll() {
        var questions = questionRepository.findAll();
        assertEquals(5, questions.size());
    }

    @Test
    void findById() {
        var question = questionRepository.findById(fromString("3cee42fb-d8da-4127-8f0e-3ddfab263f34"));
        assertTrue(question.isPresent());
        assertEquals(OPEN, question.get().getType());
        assertEquals("Question 2", question.get().getQuestion());
    }
}