package be.riddler.v1.answer.feature;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * UpdateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateAnswerFeature {
    private final AnswerRepository answerRepository;

    @Transactional
    public @NonNull Answer update(@NonNull UUID answerId, @NonNull UpdateAnswer updateAnswer) {
        var answer = answerRepository.findById(answerId);

        if (answer.isPresent()) {
            var answerEntity = answer.get();
            answerEntity.setValue(updateAnswer.value());
            answerRepository.save(answerEntity);
            return AnswerMapper.fromAnswerEntity(answerEntity);
        } else {
            throw new EntityNotFoundException("Answer with id " + answerId + " not found");
        }
    }
}
