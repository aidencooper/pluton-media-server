package net.aidencooper.pluton.mediaserver.library.persistence;

import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Set;

@DataJpaTest
@EnableJpaAuditing
public class LibraryRepositoryTests {
    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    public void shouldPersistLibrary() {
        Library library = Library.builder()
                .name("Movies")
                .type(LibraryType.MOVIES)
                .folderPaths(Set.of(
                        "C:/Media/Movies",
                        "D:/Media/Movies"
                ))
                .enabled(true)
                .build();

        Library saved = libraryRepository.save(library);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(2, saved.getFolderPaths().size());
        Assertions.assertTrue(saved.isEnabled());
    }
}
