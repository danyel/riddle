package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.domain.Answer;
import be.riddler.v1.answer.domain.CreateAnswer;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateAnswerFeature {
    private final AnswerRepository answerRepository;

    @Transactional
    public @NonNull Answer create(@NonNull CreateAnswer createAnswer) {
        var answerEntity = AnswerMapper.fromCreateAnswer(createAnswer);
        answerRepository.save(answerEntity);
        return AnswerMapper.fromAnswerEntity(answerEntity);
    }
}
