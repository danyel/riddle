package be.riddler.question.external;

import be.riddler.question.domain.Question;
import be.riddler.question.domain.QuestionType;

import java.util.UUID;

/**
 * QuestionFixture
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public class QuestionFixture {
    public static final Question QUESTION_ONE = Question.builder().id(UUID.randomUUID()).question("Question ONE").type(QuestionType.OPEN).build();
    public static final Question QUESTION_TWO = Question.builder().id(UUID.randomUUID()).question("Question TWO").type(QuestionType.SINGLE_CHOICE).build();
    public static final Question QUESTION_THREE = Question.builder().id(UUID.randomUUID()).question("Question THREE").type(QuestionType.OPEN).build();
    public static final Question QUESTION_FOUR = Question.builder().id(UUID.randomUUID()).question("Question FOUR").type(QuestionType.OPEN).build();
    public static final Question QUESTION_FIVE = Question.builder().id(UUID.randomUUID()).question("Question FIVE").type(QuestionType.SINGLE_CHOICE).build();
    public static final Question QUESTION_SIX = Question.builder().id(UUID.randomUUID()).question("Question SIX").type(QuestionType.MULTIPLE_CHOICE).build();
    public static final Question QUESTION_SEVEN = Question.builder().id(UUID.randomUUID()).question("Question SEVEN").type(QuestionType.MULTIPLE_CHOICE).build();
    public static final Question QUESTION_EIGHT = Question.builder().id(UUID.randomUUID()).question("Question EIGHT").type(QuestionType.SINGLE_CHOICE).build();
    public static final Question QUESTION_NINE = Question.builder().id(UUID.randomUUID()).question("Question NINE").type(QuestionType.MULTIPLE_CHOICE).build();
    public static final Question QUESTION_TEN = Question.builder().id(UUID.randomUUID()).question("Question TEN").type(QuestionType.MULTIPLE_CHOICE).build();
}
