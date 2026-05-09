package be.riddler.v1.participant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ParticipantEntity
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "participants")
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String storedToken;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
