package be.riddler.v1.participant.bff;

import be.riddler.v1.participant.api.ParticipantClient;
import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.question.api.QuestionClient;
import be.riddler.v1.question.domain.Question;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@BrowserCallable
//@RolesAllowed("ADMIN")
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ParticipantEndpoint {
    private final QuestionClient questionApi;
    private final ParticipantClient participantClient;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionApi.getQuestions();
    }

    public @NonNull Question get(@NonNull UUID uuid) {
        return questionApi.findById(uuid);
    }

    public @NonNull ParticipantDetail findById(@NonNull UUID uuid) {
        return participantClient.findById(uuid);
    }
}
