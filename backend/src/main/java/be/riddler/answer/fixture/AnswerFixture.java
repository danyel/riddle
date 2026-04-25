package be.riddler.answer.fixture;

import be.riddler.answer.domain.Answer;
import be.riddler.question.external.QuestionFixture;

import java.util.UUID;

/**
 * AnswerFixture
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public class AnswerFixture {
    public static final Answer ANSWER_ONE = new Answer(UUID.randomUUID(), "Answer", QuestionFixture.QUESTION_FOUR.getId());
    public static final Answer ANSWER_TWO = new Answer(UUID.randomUUID(), "Answer", QuestionFixture.QUESTION_FIVE.getId());
    public static final Answer ANSWER_THREE = new Answer(UUID.randomUUID(), "Answer", QuestionFixture.QUESTION_ONE.getId());
}
