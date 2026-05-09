package be.riddler.v1.question.api;

import be.riddler.v1.question.domain.CreateQuestion;
import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.domain.QuestionId;
import be.riddler.v1.question.domain.UpdateQuestion;
import be.riddler.v1.question.domain.UpdateWithId;
import be.riddler.v1.question.domain.feature.CreateQuestionFeature;
import be.riddler.v1.question.domain.feature.DeleteQuestionByIdFeature;
import be.riddler.v1.question.domain.feature.GetQuestionByIdFeature;
import be.riddler.v1.question.domain.feature.GetQuestionsFeature;
import be.riddler.v1.question.domain.feature.UpdateQuestionFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * QuestionResource
 *
 * @author dnoulet
 * @version 1.0.0 25/04/2026
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping(path = "/v1/questions")
class QuestionResource implements QuestionApi {
    private final CreateQuestionFeature createQuestionFeature;
    private final GetQuestionsFeature questionsFeature;
    private final DeleteQuestionByIdFeature deleteQuestionByIdFeature;
    private final GetQuestionByIdFeature getQuestionByIdFeature;
    private final UpdateQuestionFeature updateQuestionFeature;

    @Override
    public List<Question> getQuestions() {
        return questionsFeature.findAll();
    }

    @Override
    public Question findById(UUID uuid) {
        return getQuestionByIdFeature.byQuestionId(QuestionId.fromQuestionId(uuid));
    }

    @Override
    public Question create(CreateQuestion createQuestion) {
        return createQuestionFeature.executeWithReturn(createQuestion);
    }

    @Override
    public void delete(UUID id) {
        deleteQuestionByIdFeature.execute(QuestionId.fromQuestionId(id));
    }

    @Override
    public Question update(UUID id, UpdateQuestion updateQuestion) {
        return updateQuestionFeature.executeWithReturn(new UpdateWithId(QuestionId.fromQuestionId(id), updateQuestion));
    }
}
