package be.riddler.v1.fixture;

import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;

import java.util.UUID;

import static be.riddler.v1.fixture.Fixture.Answer.answerEntity;
import static be.riddler.v1.fixture.Fixture.Question.questionId;

/**
 * Fixture
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
public class Fixture {
    public static class Question {
        public static final UUID questionId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        public static final UUID dbId = UUID.fromString("a2b20b03-9d96-41d4-8e83-a35b21c21fe7");
    }

    public static class Answer {
        public static final UUID answerId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        public static final AnswerEntity answerEntity = AnswerEntity.builder()
                .id(answerId)
                .questionId(questionId)
                .build();
    }

    public static class Category {
        public static final UUID categoryId = UUID.fromString("22222222-2222-2222-2222-222222222222");
    }

    public static class Keyword {
        public static final UUID keywordId = UUID.fromString("44444444-4444-4444-4444-444444444444");
    }

    public static class Solution {
        public static final UUID solutionId = UUID.fromString("55555555-5555-5555-5555-555555555555");

        public static final SolutionEntity solutionEntity = SolutionEntity.builder()
                .value("value")
                .answer(answerEntity)
                .build();
    }
}
