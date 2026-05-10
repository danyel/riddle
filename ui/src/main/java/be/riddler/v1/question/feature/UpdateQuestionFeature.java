package be.riddler.v1.question.feature;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.domain.UpdateWithId;
import org.jspecify.annotations.NonNull;

/**
 * UpdateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface UpdateQuestionFeature {
    @NonNull Question executeWithReturn(@NonNull UpdateWithId updateWithId);
}
