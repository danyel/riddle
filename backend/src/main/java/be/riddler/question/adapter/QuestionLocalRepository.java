package be.riddler.question.adapter;

import be.riddler.question.domain.Question;
import be.riddler.question.external.QuestionFixture;
import be.riddler.question.port.QuestionRepository;
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
class QuestionLocalRepository implements QuestionRepository {
    private final List<Question> questions = List.of(
            QuestionFixture.QUESTION_ONE,
            QuestionFixture.QUESTION_TWO,
            QuestionFixture.QUESTION_THREE,
            QuestionFixture.QUESTION_FOUR,
            QuestionFixture.QUESTION_FIVE,
            QuestionFixture.QUESTION_SIX,
            QuestionFixture.QUESTION_SEVEN,
            QuestionFixture.QUESTION_EIGHT,
            QuestionFixture.QUESTION_NINE,
            QuestionFixture.QUESTION_TEN
    );

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public Question findById(UUID uuid) {
        return questions.stream().filter(i -> i.getId().equals(uuid)).findFirst().orElseThrow();
    }
}
