package net.aidencooper.pluton.mediaserver.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findByContentType(ContentType contentType);
    List<Library> findEnabledByContentType(ContentType contentType);
    List<Library> findByEnabledTrue();
    Optional<Library> findByDisplayName(String displayName);

    @Query("SELECT library FROM Library library JOIN library.folderPaths fp WHERE fp = :folderPath")
    List<Library> findByFolderPath(@Param("folderPath") String folderPath);
    boolean existsByDisplayName(String displayName);

    @Query("SELECT COUNT(library) > 0 FROM Library library JOIN library.folderPaths fp WHERE fp = :folderPath")
    boolean existsByFolderPath(String folderPath);
}
