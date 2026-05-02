package be.riddler.v1.answer.adapter.repository;

import be.riddler.common.repository.AbstractRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * AnswerRepositoryTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
class AnswerRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void findAllByQuestionId() {
        assertEquals(3, answerRepository.findAllByQuestionId(fromString("a2b20b03-9d96-41d4-8e83-a35b21c21fe7")).size());
    }
}