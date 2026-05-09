package be.riddler.v1.question.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetQuestionByIdFeature implements DomainFeature<UUID, Question> {
    private final QuestionRepository questionRepository;

    @Override
    public Question executeWithReturn(UUID uuid) {
        return questionRepository.findById(uuid).map(QuestionMapper::fromQuestionEntity).orElseThrow(EntityNotFoundException::new);
    }
}
