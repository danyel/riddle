package be.riddler.v1.question.domain.feature;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetQuestionsFeature {
    private final QuestionRepository questionRepository;

    public @NonNull List<@NonNull Question> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::fromQuestionEntity)
                .toList();
    }
}
