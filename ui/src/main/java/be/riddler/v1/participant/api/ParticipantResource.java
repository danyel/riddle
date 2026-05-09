package be.riddler.v1.participant.api;

import be.riddler.v1.participant.domain.CreateParticipant;
import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.domain.ParticipantId;
import be.riddler.v1.participant.feature.CreateParticipantFeature;
import be.riddler.v1.participant.feature.FindAllParticipantsFeature;
import be.riddler.v1.participant.feature.FindByParticipantIdFeature;
import be.riddler.v1.participant.feature.GenerateTokenFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequestMapping("/v1/participants")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ParticipantResource implements ParticipantApi {
    private final FindByParticipantIdFeature findByParticipantIdFeature;
    private final FindAllParticipantsFeature findAllParticipantsFeature;
    private final GenerateTokenFeature generateTokenFeature;
    private final CreateParticipantFeature createParticipantFeature;

    @Override
    public @NonNull List<@NonNull ParticipantDetail> findAll() {
        return findAllParticipantsFeature.findAll();
    }

    @Override
    public void generateToken(@NonNull UUID participantId) {
        generateTokenFeature.generate(ParticipantId.from(participantId));
    }

    @Override
    public @NonNull ParticipantDetail findById(@NonNull UUID participantId) {
        return findByParticipantIdFeature.findByParticipantId(ParticipantId.from(participantId));
    }

    @Override
    public @NonNull ParticipantDetail create(@NonNull CreateParticipant participantDetail) {
        return createParticipantFeature.create(participantDetail);
    }
}
