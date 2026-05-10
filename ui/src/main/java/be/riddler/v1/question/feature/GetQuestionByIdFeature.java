package be.riddler.v1.question.feature;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.domain.QuestionId;
import org.jspecify.annotations.NonNull;

/**
 * GetQuestionByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GetQuestionByIdFeature {
    @NonNull Question byQuestionId(@NonNull QuestionId questionId);
}
