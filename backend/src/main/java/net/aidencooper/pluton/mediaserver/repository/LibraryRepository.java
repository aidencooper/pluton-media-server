package net.aidencooper.pluton.mediaserver.repository;

import net.aidencooper.pluton.mediaserver.domain.entity.LibraryType;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByName(String name);
    List<Library> findByType(LibraryType type);
    List<Library> findByEnabledTrue();

    @Query("SELECT library FROM Library library JOIN library.folderPaths fp WHERE fp = :folderPath")
    List<Library> findByFolderPath(@Param("folderPath") String folderPath);
}
