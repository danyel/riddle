package be.riddler.v1.answer.feature.impl;

import be.riddler.v1.answer.client.model.UpdateAnswer;
import be.riddler.v1.answer.client.model.UpdateSolution;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;
import be.riddler.v1.answer.repository.AnswerRepository;
import be.riddler.v1.answer.repository.SolutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.riddler.v1.fixture.Fixture.Answer.answerId;
import static be.riddler.v1.fixture.Fixture.Solution.solutionId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UpdateAnswerFeatureTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Update Answer Feature")
@ExtendWith(MockitoExtension.class)
class UpdateAnswerFeatureTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private SolutionRepository solutionRepository;
    @InjectMocks
    private UpdateAnswerFeatureImpl updateAnswerFeature;
    @Captor
    private ArgumentCaptor<SolutionEntity> onAddSolutionCaptor;

    /**
     * Given:
     * - existing answer in the database with one solution which we want to update
     * - update answer contains 2 solutions: one to update and one to add
     * When:
     * Updating the answer of specific id (11111111-1111-1111-1111-111111111111)
     * Then:
     * - first fetch the answer for 11111111-1111-1111-1111-111111111111
     * - solutions contains 1 element with id (22222222-2222-2222-2222-222222222222)
     * - value for solution 22222222-2222-2222-2222-222222222222 will change from "v" -> "value"
     * - next option to add will create a Solution, and it will set the value to "value2"
     * - after all the solutions have been processed than the answer will be saved.
     */
    @DisplayName("Given an existing answer where we want to add and update an option when updating the answer then a solution has been saved and a new one will be added to the table")
    @Test
    void update() {
        var answerEntity = AnswerEntity.builder()
                .id(answerId)
                .solutions(new ArrayList<>())
                .questionId(UUID.randomUUID())
                .build();
        answerEntity.getSolutions().add(SolutionEntity.builder().id(solutionId).answer(answerEntity).value("v").build());
        var newSolutionId = UUID.randomUUID();
        var newSolutions = SolutionEntity.builder().value("value2").id(newSolutionId).build();
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(answerEntity));
        var mappedSolution = SolutionEntity.builder().value("value2").build();
        when(solutionRepository.save(eq(mappedSolution))).thenReturn(newSolutions);
        var updateAnswer = new UpdateAnswer(List.of(new UpdateSolution(solutionId, "value"), new UpdateSolution(null, "value2")));

        var answer = updateAnswerFeature.update(answerId, updateAnswer);

        var inOrder = inOrder(solutionRepository, answerRepository);
        inOrder.verify(answerRepository).findById(answerId);
        inOrder.verify(solutionRepository).save(onAddSolutionCaptor.capture());
        inOrder.verify(answerRepository).save(eq(answerEntity));
        inOrder.verifyNoMoreInteractions();
        verify(solutionRepository).save(onAddSolutionCaptor.capture());
        SolutionEntity solution = onAddSolutionCaptor.getValue();
        assertEquals("value2", solution.getValue());
        assertEquals(answerEntity, solution.getAnswer());
        assertEquals("value", answer.solutions().getFirst().value());
        assertEquals("value2", answer.solutions().getLast().value());
        assertEquals(newSolutionId, answer.solutions().getLast().id());
    }

    /**
     * Given:
     * - answer does not exist in the database
     * When:
     * Updating the answer of specific id (11111111-1111-1111-1111-111111111111)
     * Then:
     * IllegalStateException will be thrown
     */
    @DisplayName("Given an invalid id when updating option then illegal state exception will be thrown")
    @Test
    void updateNoValueFound() {
        when(answerRepository.findById(answerId)).thenReturn(Optional.empty());
        var updateAnswer = new UpdateAnswer(List.of());

        var illegalStateException = Assertions.assertThrows(IllegalStateException.class, () -> updateAnswerFeature.update(answerId, updateAnswer));

        var inOrder = inOrder(solutionRepository, answerRepository);
        inOrder.verify(answerRepository).findById(answerId);
        inOrder.verifyNoMoreInteractions();
        assertEquals("Answer with id " + answerId + " not found", illegalStateException.getMessage());
    }
}