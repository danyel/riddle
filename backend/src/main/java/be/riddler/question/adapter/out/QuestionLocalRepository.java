package be.riddler.question.adapter.out;

import be.riddler.question.domain.Question;
import be.riddler.question.domain.QuestionType;
import be.riddler.question.port.out.QuestionRepository;
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
public class QuestionLocalRepository implements QuestionRepository {
    @Override
    public List<Question> getQuestions() {
        return List.of(
                Question.builder()
                        .id(UUID.randomUUID())
                        .question("What?")
                        .type(QuestionType.OPEN)
                        .build(),
                Question.builder()
                        .id(UUID.randomUUID())
                        .question("What single?")
                        .type(QuestionType.SINGLE_CHOICE)
                        .build(),
                Question.builder()
                        .id(UUID.randomUUID())
                        .question("What? multiple?😇")
                        .type(QuestionType.MULTIPLE_CHOICE)
                        .build()
        );
    }
}
