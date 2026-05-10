package be.riddler.v1.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Getter
@Setter
@MappedSuperclass // 💡 Required so child entities inherit these columns
@EntityListeners(AuditingEntityListener.class) // 💡 Intercepts save/update actions
public abstract class BaseEntity {
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private String updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
