package be.riddler.question.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Getter
public class Question {
    private UUID id;
    private String question;
    private QuestionType type;
}
