package net.aidencooper.pluton.mediaserver.library.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "libraries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {
    // Database

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Object

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LibraryType type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "library_folder_paths",
            joinColumns = @JoinColumn(name = "library_id")
    )
    @Column(name = "folder_paths", nullable = false)
    private Set<String> folderPaths;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
}
