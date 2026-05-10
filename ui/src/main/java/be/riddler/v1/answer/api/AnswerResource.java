package be.riddler.v1.answer.api;

import be.riddler.v1.answer.client.AnswerClient;
import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.QuestionId;
import be.riddler.v1.answer.client.model.UpdateAnswer;
import be.riddler.v1.answer.feature.CreateAnswerFeature;
import be.riddler.v1.answer.feature.GetAnswersByQuestionIdFeature;
import be.riddler.v1.answer.feature.UpdateAnswerFeature;
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
class AnswerResource implements AnswerClient {
    private final CreateAnswerFeature createAnswerFeature;
    private final GetAnswersByQuestionIdFeature getAnswersByQuestionIdFeature;
    private final UpdateAnswerFeature updateAnswerFeature;

    @Override
    public List<Answer> findByQuestionId(UUID questionId) {
        return getAnswersByQuestionIdFeature.byQuestionId(new QuestionId(questionId));
    }

    @Override
    public Answer create(CreateAnswer createAnswer) {
        return createAnswerFeature.create(createAnswer);
    }

    @Override
    public Answer update(UUID answerId, UpdateAnswer updateAnswer) {
        return updateAnswerFeature.update(answerId, updateAnswer);
    }
}
