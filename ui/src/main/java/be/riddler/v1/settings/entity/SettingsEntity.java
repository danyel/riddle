package be.riddler.v1.settings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * SettingsEntity
 *
 * @author dnoulet
 * @version 1.0.0 14/05/2026
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "settings")
public class SettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(targetEntity = BookmarkEntity.class, mappedBy = "settings", orphanRemoval = true)
    @Builder.Default
    private List<BookmarkEntity> bookmarks = new ArrayList<>();
    private String username;

    public @NonNull List<@NonNull BookmarkEntity> getBookmarks() {
        if (bookmarks == null) {
            bookmarks = new ArrayList<>();
        }
        return bookmarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SettingsEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
