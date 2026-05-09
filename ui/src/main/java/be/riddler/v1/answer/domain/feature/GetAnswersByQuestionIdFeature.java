package be.riddler.v1.answer.domain.feature;

import be.riddler.v1.answer.domain.Answer;
import be.riddler.v1.answer.domain.QuestionId;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.common.domain.feature.DomainFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
public class GetAnswersByQuestionIdFeature implements DomainFeature<QuestionId, List<Answer>> {
    private final AnswerRepository answerRepository;

    @Transactional
    @Override
    public List<Answer> executeWithReturn(QuestionId questionId) {
        return answerRepository.findAllByQuestionId(questionId.id())
                .stream()
                .map(AnswerMapper::fromAnswerEntity)
                .toList();
    }
}
