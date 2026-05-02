package be.riddler.v1.question.domain;

import be.riddler.api.v1.question.QuestionType;
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
@ToString
@EqualsAndHashCode
@Getter
public class Question {
    private UUID id;
    private String question;
    private QuestionType type;
    private boolean multipleChoice;
    private boolean singleChoice;
    private boolean open;

    public Question(String question, QuestionType type) {
        this(null, question, type);
    }

    public Question(UUID id, String question, QuestionType type) {
        this.id = id;
        this.question = question;
        this.type = type;
        multipleChoice = QuestionType.MULTIPLE_CHOICE.equals(type);
        singleChoice = QuestionType.SINGLE_CHOICE.equals(type);
        open = QuestionType.OPEN.equals(type);
    }
}
