package be.riddler.v1.question.domain;

/**
 * UpdateWithId
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record UpdateWithId(QuestionId questionId, UpdateQuestion question) {
}
