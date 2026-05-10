package be.riddler.v1.invitation.bff;

import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.InvitationDetail;
import be.riddler.v1.question.api.QuestionClient;
import be.riddler.v1.question.domain.Question;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * InvitationEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@BrowserCallable
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class InvitationEndpoint {
    private final InvitationClient invitationClient;
    private final QuestionClient questionClient;

    public @NonNull InvitationDetail create(@NonNull CreateInvitation createInvitation) {
        return invitationClient.create(createInvitation);
    }

    public @NonNull InvitationDetail findById(@NonNull UUID invitationId) {
        return invitationClient.findById(invitationId);
    }

    public void delete(@NonNull UUID invitationId) {
        invitationClient.deleteById(invitationId);
    }

    public @NonNull List<@NonNull InvitationDetail> findInvitationsByParticipantId(@NonNull UUID participantId) {
        return invitationClient.findInvitationsByParticipantId(participantId);
    }

    public @NonNull List<@NonNull Question> findByIds(@NonNull List<@NonNull UUID> questionIds) {
        return questionClient.getQuestionsById(questionIds);
    }
}
