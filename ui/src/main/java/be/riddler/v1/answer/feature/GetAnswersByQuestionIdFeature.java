package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.QuestionId;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CreateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetAnswersByQuestionIdFeature {
    private final AnswerRepository answerRepository;

    @Transactional
    public @NonNull List<@NonNull Answer> byQuestionId(@NonNull QuestionId questionId) {
        return answerRepository.findAllByQuestionId(questionId.id())
                .stream()
                .map(AnswerMapper::fromAnswerEntity)
                .toList();
    }
}
