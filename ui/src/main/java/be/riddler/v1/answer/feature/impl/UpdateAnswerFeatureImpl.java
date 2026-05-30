package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import be.riddler.v1.answer.entity.SolutionEntity;
import be.riddler.v1.answer.feature.UpdateAnswerFeature;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * UpdateAnswerFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = PACKAGE)
class UpdateAnswerFeatureImpl implements UpdateAnswerFeature {
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    @Transactional
    @Override
    public @NonNull Answer update(@NonNull UUID answerId, @NonNull UpdateAnswer updateAnswer) {
        var answer = answerRepository.findById(answerId);

        if (answer.isPresent()) {
            var answerEntity = answer.get();
            updateAnswer.values()
                    .forEach(updateSolution -> {
                        if (updateSolution.id() != null) {
                            answerEntity.getSolutions()
                                    .stream()
                                    .filter(s -> s.getId().equals(updateSolution.id()))
                                    .findFirst()
                                    .ifPresent(solutionEntity -> {
                                        if (!Objects.equals(solutionEntity.getValue(), updateSolution.value())) {
                                            solutionEntity.setValue(updateSolution.value());
                                        }
                                    });
                        } else {
                            answerEntity.getSolutions().add(solutionRepository.save(SolutionEntity.builder()
                                    .value(updateSolution.value())
                                    .answer(answerEntity)
                                    .build()));
                        }
                    });
            answerRepository.save(answerEntity);
            return AnswerMapper.fromAnswerEntity(answerEntity);
        } else {
            throw new IllegalStateException("Answer with id " + answerId + " not found");
        }
    }
}
