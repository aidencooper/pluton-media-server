package net.aidencooper.pluton.mediaserver.library;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "libraries")
@Getter
@Setter
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "library_folder_paths",
            joinColumns = @JoinColumn(name = "library_id")
    )
    @Column(name = "folder_paths", nullable = false)
    private List<String> folderPaths;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Library() {}

    public Library(ContentType contentType, List<String> folderPath, String displayName) {
        this.contentType = contentType;
        this.folderPaths = folderPath;
        this.displayName = displayName;
    }
}
