package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.client.model.CreateSolution;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CreateAnswerFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@ExtendWith(MockitoExtension.class)
class CreateAnswerFeatureTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @InjectMocks
    private CreateAnswerFeatureImpl createAnswerFeature;
    @Captor
    private ArgumentCaptor<AnswerEntity> onAddAnswerCaptor;
    @Captor
    private ArgumentCaptor<SolutionEntity> onAddSolutionCaptor;

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
        var questionId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var answerId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        var createAnswer = new CreateAnswer(List.of(new CreateSolution("value")), questionId);
        var answerEntity = mock(AnswerEntity.class);
        when(answerEntity.getId()).thenReturn(answerId);
        when(answerRepository.save(any(AnswerEntity.class))).thenReturn(answerEntity);
        when(answerRepository.save(answerEntity)).thenReturn(answerEntity);

        createAnswerFeature.create(createAnswer);

        var inOrder = inOrder(solutionRepository, answerRepository);
        inOrder.verify(answerRepository).save(onAddAnswerCaptor.capture());
        inOrder.verify(solutionRepository).save(onAddSolutionCaptor.capture());
        inOrder.verify(answerRepository).save(onAddAnswerCaptor.capture());
        inOrder.verifyNoMoreInteractions();
    }
}