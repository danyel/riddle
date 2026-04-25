package be.riddler.answer.adapter;

import be.riddler.answer.domain.Answer;
import be.riddler.answer.fixture.AnswerFixture;
import be.riddler.answer.port.AnswerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * QuestionLocalRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@Component
@Profile("demo")
class AnswerLocalRepository implements AnswerRepository {
    @Override
    public List<Answer> findByQuestion(UUID questionId) {
        return List.of(AnswerFixture.ANSWER_ONE, AnswerFixture.ANSWER_TWO, AnswerFixture.ANSWER_THREE);
    }
}
