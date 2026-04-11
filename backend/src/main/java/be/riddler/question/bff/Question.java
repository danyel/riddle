package be.riddler.question.bff;

import be.riddler.question.domain.QuestionType;

import java.util.UUID;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Question(UUID id, String question, QuestionType type) {
}
