package be.riddler.v1.invitation.entity;

import be.riddler.v1.common.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * InvitationEntity
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "invitations")
public class InvitationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID participantId;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "invitation_questions",
            joinColumns = @JoinColumn(name = "invitation_id")
    )
    @Column(name = "question_id")
    private List<UUID> questions;
}
