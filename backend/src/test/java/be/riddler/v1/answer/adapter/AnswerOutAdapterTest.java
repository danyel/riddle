package be.riddler.v1.answer.adapter;

import be.riddler.v1.answer.adapter.AnswerOutAdapter;
import be.riddler.v1.answer.adapter.repository.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;

/**
 * AnswerOutAdapterTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@ExtendWith(MockitoExtension.class)
class AnswerOutAdapterTest {
    @Mock
    private AnswerRepository answerRepository;
    @InjectMocks
    private AnswerOutAdapter answerOutAdapter;

    @DisplayName("When executing findByQuestion then findAllByQuestionId will be called")
    @Test
    void findByQuestion() {
        var questionId = UUID.randomUUID();

        answerOutAdapter.findByQuestion(questionId);

        Mockito.verify(answerRepository).findAllByQuestionId(eq(questionId));
    }
}