package be.riddler.v1.invitation.bff;

import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.question.client.QuestionClient;
import be.riddler.v1.question.client.model.Question;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantInvitationEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ParticipantInvitationEndpoint {
    private final InvitationClient invitationClient;
    private final QuestionClient questionClient;

    public @NonNull List<@NonNull Question> findByIds(@NonNull List<@NonNull UUID> questionIds) {
        return questionClient.getQuestionsById(questionIds);
    }

    public @NonNull Invitation findById(@NonNull UUID invitationId) {
        return invitationClient.findById(invitationId);
    }
}
