package be.riddler.v1.participant.entity;

import be.riddler.v1.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * ParticipantEntity
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "participants")
public class ParticipantEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String storedToken;
    private byte[] photo;
    private byte[] cv;
}
