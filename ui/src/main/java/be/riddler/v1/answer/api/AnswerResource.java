package be.riddler.v1.answer.api;

import be.riddler.v1.answer.domain.Answer;
import be.riddler.v1.answer.domain.CreateAnswer;
import be.riddler.v1.answer.domain.QuestionId;
import be.riddler.v1.answer.domain.feature.CreateAnswerFeature;
import be.riddler.v1.answer.domain.feature.GetAnswersByQuestionIdFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * AnswerResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequestMapping(path = "/v1/answers")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AnswerResource implements AnswerApi {
    private final CreateAnswerFeature createAnswerFeature;
    private final GetAnswersByQuestionIdFeature getAnswersByQuestionIdFeature;


    @Override
    public List<Answer> findByQuestionId(UUID questionId) {
        return getAnswersByQuestionIdFeature.byQuestionId(new QuestionId(questionId));
    }

    @Override
    public Answer create(CreateAnswer createAnswer) {
        return createAnswerFeature.create(createAnswer);
    }
}
