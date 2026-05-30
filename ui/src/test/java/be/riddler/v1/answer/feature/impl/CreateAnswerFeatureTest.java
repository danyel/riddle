package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.answer.client.model.Solution;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.mapper.AnswerMapper;
import be.riddler.v1.answer.mapper.SolutionMapper;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.riddler.v1.fixture.Fixture.Answer.answerId;
import static be.riddler.v1.fixture.Fixture.Question.questionId;
import static be.riddler.v1.fixture.Fixture.Solution.solutionEntity;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;

/**
 * CreateAnswerFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Create Answer Feature")
@ExtendWith(MockitoExtension.class)
class CreateAnswerFeatureTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @InjectMocks
    private CreateAnswerFeatureImpl ut;

    /**
     * Given:
     * - valid create answer with one solution
     * When:
     * Creating an answer for a question id (11111111-1111-1111-1111-111111111111)
     * Then:
     * - saves first the answer
     * - solutions will be saved
     */
    @DisplayName("Given a create answer where we want to add when the answer will be saves")
    @Test
    void create() {
        var createAnswer = new CreateAnswer(List.of(new CreateSolution("value")), questionId);
        var answerEntity = AnswerEntity.builder()
                .questionId(questionId)
                .build();

        try (MockedStatic<AnswerMapper> answerMapper = Mockito.mockStatic(AnswerMapper.class)) {
            try (MockedStatic<SolutionMapper> solutionMapper = Mockito.mockStatic(SolutionMapper.class)) {
                answerMapper.when(() -> AnswerMapper.fromCreateAnswer(eq(createAnswer))).thenReturn(answerEntity);
                solutionMapper.when(() -> SolutionMapper.fromCreateSolutionToEntity(eq(new CreateSolution("value")), eq(answerEntity)))
                        .thenReturn(solutionEntity);
                answerMapper.when(() -> AnswerMapper.fromAnswerEntity(eq(Fixture.Answer.answerEntity)))
                        .thenReturn(new Answer(answerId, List.of(new Solution(solutionEntity.getId(), solutionEntity.getValue())), questionId));

                ut.create(createAnswer);

                var inOrder = inOrder(solutionRepository, answerRepository);
                inOrder.verify(answerRepository).save(answerEntity);
                inOrder.verify(solutionRepository).save(solutionEntity);
                inOrder.verify(answerRepository).save(answerEntity);
                inOrder.verifyNoMoreInteractions();
            }
        }
    }
}